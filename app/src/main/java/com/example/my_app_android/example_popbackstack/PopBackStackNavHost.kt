package com.example.my_app_android.example_popbackstack

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun PopBackStackNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { backStackEntry ->
            PopBackStackHomeScreen(navController = navController, backStackEntry)
        }
        composable("profile") { backStackEntry ->
            PopBackStackProfileScreen(navController = navController)
        }
    }
}