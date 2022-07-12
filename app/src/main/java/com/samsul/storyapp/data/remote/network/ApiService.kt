package com.samsul.storyapp.data.remote.network

import com.samsul.storyapp.data.remote.model.home.ResponseHome
import com.samsul.storyapp.data.remote.model.login.ResponseLogin
import com.samsul.storyapp.data.remote.model.register.ResponseRegister
import com.samsul.storyapp.data.remote.model.upload.ResponseUploadStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : ResponseRegister

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : ResponseLogin

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") auth: String
    ) : ResponseHome

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : ResponseUploadStory


}