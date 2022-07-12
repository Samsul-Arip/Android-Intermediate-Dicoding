package com.samsul.storyapp.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samsul.storyapp.data.remote.model.home.ListStory
import com.samsul.storyapp.databinding.ItemStoryBinding

class StoryAdapter(private val context: Context, private val clickListener: OnItemClickAdapter) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private val listStory = ArrayList<ListStory>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ListStory>) {
        listStory.clear()
        listStory.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StoryViewHolder(
        ItemStoryBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int = listStory.size

    inner class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listStory: ListStory) {
            with(binding) {
                tvName.text = listStory.name
                tvDescription.text = listStory.description
                Glide.with(context)
                    .load(listStory.photoUrl)
                    .into(imgPhotos)
                root.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        context as Activity,
                        Pair(binding.imgPhotos, "image"),
                        Pair(binding.tvName, "name"),
                        Pair(binding.tvDescription, "description")
                    )
                    clickListener.onItemClicked(listStory, optionsCompat)
                }
            }
        }
    }

    interface OnItemClickAdapter {
        fun onItemClicked(listStory: ListStory, optionsCompat: ActivityOptionsCompat)
    }
}