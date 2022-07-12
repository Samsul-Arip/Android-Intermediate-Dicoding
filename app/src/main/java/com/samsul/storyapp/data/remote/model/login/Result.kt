package com.samsul.storyapp.data.remote.model.login

import com.google.gson.annotations.SerializedName

data class Result(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String,
)
