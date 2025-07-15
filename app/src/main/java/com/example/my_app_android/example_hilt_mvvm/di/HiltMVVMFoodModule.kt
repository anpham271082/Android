package com.example.my_app_android.example_hilt_mvvm.di

import android.content.Context
import com.example.my_app_android.example_hilt_mvvm.data.api.HiltMVVMFoodApi
import com.example.my_app_android.example_hilt_mvvm.data.api.HiltMVVMFoodApi.Companion.API_URL
import com.example.my_app_android.example_hilt_mvvm.data.local.HiltMVVMDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class HiltMVVMFoodModule {
    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodMenuApiService(
        retrofit: Retrofit
    ): HiltMVVMFoodApi.Service {
        return retrofit.create(HiltMVVMFoodApi.Service::class.java)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): HiltMVVMDataStore {
        return HiltMVVMDataStore(context)
    }






}