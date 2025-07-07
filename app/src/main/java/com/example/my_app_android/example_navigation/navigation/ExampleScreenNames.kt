package com.example.my_app_android.example_navigation.navigation

import kotlinx.serialization.Serializable

@Serializable object EXAMPLE_AUTH
@Serializable object EXAMPLE_ROOT
@Serializable object EXAMPLE_HOME
@Serializable object EXAMPLE_LOGIN
@Serializable object EXAMPLE_SIGNUP
@Serializable
data class ExampleDetail(
    val name: String,
    val age: Int
)