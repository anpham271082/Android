package com.example.my_app_android.example_mvvm3.data.repository

import com.example.my_app_android.example_mvvm3.data.api.ExampleMVVM3ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExampleMVVM3RetrofitInstance {
    val api: ExampleMVVM3ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // Base URL for the API
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson Converter
            .build()
            .create(ExampleMVVM3ApiService::class.java)
    }
}