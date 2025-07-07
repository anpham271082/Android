package com.example.my_app_android.example_navigation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun ExampleNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = EXAMPLE_AUTH
    ) {
        exampleHomeNavGraph(navController = navController)
        exampleAuthNavGraph(navController = navController)
    }
}