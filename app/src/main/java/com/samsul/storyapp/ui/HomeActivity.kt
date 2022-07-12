package com.samsul.storyapp.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.samsul.storyapp.R
import com.samsul.storyapp.data.DataRepository
import com.samsul.storyapp.data.remote.model.home.ListStory
import com.samsul.storyapp.data.remote.network.ApiClient
import com.samsul.storyapp.databinding.ActivityHomeBinding
import com.samsul.storyapp.ui.adapter.StoryAdapter
import com.samsul.storyapp.utils.Message
import com.samsul.storyapp.utils.NetworkResult
import com.samsul.storyapp.utils.PrefsManager
import com.samsul.storyapp.utils.ViewModelFactory
import com.samsul.storyapp.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity(), StoryAdapter.OnItemClickAdapter {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private lateinit var prefsManager: PrefsManager
    private lateinit var viewModel: HomeViewModel
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        prefsManager = PrefsManager(this)
        storyAdapter = StoryAdapter(this, this)
        val dataRepository = DataRepository(ApiClient.getInstance())
        viewModel = ViewModelProvider(this, ViewModelFactory(dataRepository))[HomeViewModel::class.java]
        fetchData(prefsManager.token)

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            fetchData(prefsManager.token)
        }

        binding.btnTry.setOnClickListener {
            setLoadingState(true)
            fetchData(prefsManager.token)
        }

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    private fun fetchData(auth: String) {
        binding.rvStory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = storyAdapter
        }
        viewModel.apply {
            setLoadingState(true)
            fetchListStory(auth)
            responseListStory.observe(this@HomeActivity) {
                when(it) {
                    is NetworkResult.Success -> {
                        if(it.data?.listStory != null) {
                            storyAdapter.setData(it.data.listStory)
                            binding.btnTry.visibility = View.GONE
                        } else {
                            binding.btnTry.visibility = View.GONE
                            binding.rvStory.visibility = View.GONE
                            binding.tvNotFound.visibility = View.VISIBLE
                        }
                        binding.tvError.visibility = View.GONE
                        setLoadingState(false)
                        binding.swipeRefresh.isRefreshing = false
                    }
                    is NetworkResult.Loading -> {
                        setLoadingState(true)
                        binding.swipeRefresh.isRefreshing = true
                    }
                    is NetworkResult.Error -> {
                        setLoadingState(false)
                        binding.rvStory.visibility = View.GONE
                        binding.tvNotFound.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.btnTry.visibility = View.VISIBLE
                        binding.swipeRefresh.isRefreshing = false
                    }
                }
            }

        }
    }
    private fun setLoadingState(loading: Boolean) {
        when(loading) {
            true -> {
                binding.rvStory.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
            false -> {
                binding.rvStory.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.logout -> {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle(resources.getString(R.string.log_out))
                dialog.setMessage(getString(R.string.are_you_sure))
                dialog.setPositiveButton(getString(R.string.yes)) {_,_ ->
                    prefsManager.clear()
                    startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                    this@HomeActivity.finish()
                    Message.setMessage(this, getString(R.string.log_out_warning))
                }
                dialog.setNegativeButton(getString(R.string.no)) {_,_ ->
                    Message.setMessage(this, getString(R.string.not_out))
                }
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(listStory: ListStory, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ITEM, listStory)
        startActivity(intent, optionsCompat.toBundle())
    }

}