package com.example.my_app_android.example_mvvm1

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ExampleMVVM1ApiService {
    @GET("api/random")
    suspend fun getRandomQuote(): Response<List<ExampleMVVM1Quote>>

    companion object {
        private const val BASE_URL = "https://zenquotes.io/"

        fun create(): ExampleMVVM1ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // Gson Converter
                .build()

            return retrofit.create(ExampleMVVM1ApiService::class.java)
        }
    }
}

data class ExampleMVVM1Quote(
    val q: String?,
    val a: String?,
    val h: String?
)