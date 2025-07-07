package com.example.my_app_android.example_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.my_app_android.example_navigation.navigation.ExampleNavGraph
import com.example.my_app_android.ui.theme.My_app_androidTheme

@Composable
fun ExampleNavigation() {
    My_app_androidTheme {
        val navController = rememberNavController()
        ExampleNavGraph(navController = navController)
    }
}