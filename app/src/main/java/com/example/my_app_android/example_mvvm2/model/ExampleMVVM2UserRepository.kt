package com.example.my_app_android.example_mvvm2.model

import kotlinx.coroutines.delay

class ExampleMVVM2UserRepository {
    suspend fun fetchUserData(): ExampleMVVM2UserData {
        delay(1000)
        return ExampleMVVM2UserData("John", 25 )
    }
}