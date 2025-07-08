package com.example.my_app_android.example_navigation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.my_app_android.example_navigation.ui.ExampleLoginScreen
import com.example.my_app_android.example_navigation.ui.ExampleSignupScreen

fun NavGraphBuilder.exampleAuthNavGraph(
    navController: NavHostController
){
    navigation<EXAMPLE_AUTH>(
        startDestination = EXAMPLE_LOGIN
    ){
        composable<EXAMPLE_LOGIN>{
            ExampleLoginScreen(navController = navController)
        }
        composable<EXAMPLE_SIGNUP>{
            ExampleSignupScreen(navController = navController)
        }
    }
}