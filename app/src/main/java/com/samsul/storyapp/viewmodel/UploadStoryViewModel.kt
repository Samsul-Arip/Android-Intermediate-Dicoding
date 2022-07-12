package com.samsul.storyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.samsul.storyapp.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class UploadStoryViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    suspend fun uploadStory(auth: String, description: String, lat: String?, lon: String?, file: MultipartBody.Part) =
        dataRepository.uploadStory(auth, description, lat, lon, file)


}