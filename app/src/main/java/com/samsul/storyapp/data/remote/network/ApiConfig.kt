package com.samsul.storyapp.data.remote.network

import com.samsul.storyapp.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response

@Suppress("BlockingMethodInNonBlockingContext")
abstract class ApiConfig {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T> ) : NetworkResult<T> {
        try {
            val response = apiCall()

            if(response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error(getErrorMessage(message = response.errorBody()!!.string()))
        } catch (e: Exception) {
            return error("${e.message} : $e")
        }
    }

    private fun getErrorMessage(message: String) : String {
        val obj = JSONObject(message)
        return obj.getString("message")
    }

    private fun <T> error(errorMessage: String, data : T? = null) : NetworkResult<T> =
        NetworkResult.Error(errorMessage, data)
}