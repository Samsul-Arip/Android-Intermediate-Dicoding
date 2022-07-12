package com.samsul.storyapp.data.remote.model.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListStory(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createAt: String,

    @field:SerializedName("lat")
    val latitude: Double?,

    @field:SerializedName("lon")
    val longitude: Double?

) : Parcelable
