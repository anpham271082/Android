package com.example.my_app_android.example_navigation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.my_app_android.example_navigation.screens.ExampleDetailScreen
import com.example.my_app_android.example_navigation.screens.ExampleHomeScreen

fun NavGraphBuilder.exampleHomeNavGraph(
    navController: NavHostController
){
    navigation<EXAMPLE_ROOT>(
        startDestination = EXAMPLE_HOME
    ){
        composable<EXAMPLE_HOME>{
            ExampleHomeScreen(navController = navController)
        }
        composable<ExampleDetail>{
            val args = it.toRoute<ExampleDetail>()
            ExampleDetailScreen(navController = navController, args.age, args.name)
        }
    }
}