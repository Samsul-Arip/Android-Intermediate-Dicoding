package com.samsul.storyapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samsul.storyapp.data.DataRepository
import com.samsul.storyapp.viewmodel.HomeViewModel
import com.samsul.storyapp.viewmodel.LoginAndRegisterViewModel
import com.samsul.storyapp.viewmodel.UploadStoryViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val dataRepository: DataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginAndRegisterViewModel::class.java) -> {
                LoginAndRegisterViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(UploadStoryViewModel::class.java) -> {
                UploadStoryViewModel(dataRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Class ViewModel not Implement")
            }
        }
    }

}