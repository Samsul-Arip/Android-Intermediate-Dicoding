package com.samsul.storyapp.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.samsul.storyapp.R
import com.samsul.storyapp.databinding.FragmentSettingsBinding
import com.samsul.storyapp.ui.LoginActivity
import com.samsul.storyapp.utils.Message
import com.samsul.storyapp.utils.PrefsManager

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefsManager: PrefsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.title = resources.getString(R.string.settings)
        }
        binding.toolbar.apply {
            setTitleTextColor(Color.WHITE)
            setSubtitleTextColor(Color.WHITE)
        }
        prefsManager = PrefsManager(requireContext())
        binding.logOut.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle(resources.getString(R.string.log_out))
            dialog.setMessage(getString(R.string.are_you_sure))
            dialog.setPositiveButton(getString(R.string.yes)) { _, _ ->
                prefsManager.clear()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                activity?.finish()
                Message.setMessage(requireContext(), getString(R.string.log_out_warning))
            }
            dialog.setNegativeButton(getString(R.string.no)) { _, _ ->
                Message.setMessage(requireContext(), getString(R.string.not_out))
            }
            dialog.show()
        }
        binding.settingsLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}