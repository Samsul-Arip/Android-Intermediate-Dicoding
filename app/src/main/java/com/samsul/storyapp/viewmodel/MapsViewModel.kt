package com.samsul.storyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.samsul.storyapp.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {
    suspend fun getStoriesLocation(auth: String) = dataRepository.getStoriesLocation(auth)
}