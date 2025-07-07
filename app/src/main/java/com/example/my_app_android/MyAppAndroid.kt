package com.example.my_app_android

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyAppAndroid : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("MyAppAndroid", "onCreate")
    }
}