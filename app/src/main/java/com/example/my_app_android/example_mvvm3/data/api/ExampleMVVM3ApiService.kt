package com.example.my_app_android.example_mvvm3.data.api

import com.example.my_app_android.example_mvvm3.data.api.model.ExampleMVVM3PostData
import retrofit2.http.GET

// Define the Retrofit API interface
interface ExampleMVVM3ApiService {
    @GET("posts")
    suspend fun getPosts(): List<ExampleMVVM3PostData>
}