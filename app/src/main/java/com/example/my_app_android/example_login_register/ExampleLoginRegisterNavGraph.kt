package com.example.my_app_android.example_login_register

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun ExampleLoginRegisterNavGraph( paddingValues: PaddingValues){
    val navController = rememberNavController()
    NavHost (navController, startDestination = "login") {
        composable("login") { ExampleLoginScreen(navController, paddingValues)}
        composable("register") { ExampleRegisterScreen(navController, paddingValues)}
    }
}