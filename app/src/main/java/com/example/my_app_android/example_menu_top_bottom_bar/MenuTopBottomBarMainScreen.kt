package com.example.my_app_android.example_menu_top_bottom_bar

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
@Composable
fun MenuTopBottomBarMainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val bottomItems = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Settings", Icons.Default.Settings, "settings"),
        BottomNavItem("Profile", Icons.Default.Person, "profile")
    )

    Scaffold(
        topBar = {
            TopAppBarWithMenu(
                title = "My App",
                onMenuItemClick = { selected ->
                    Toast.makeText(context, "Clicked $selected", Toast.LENGTH_SHORT).show()
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, bottomItems)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") { MenuTopBottomBarScreenContent("Home Screen") }
            composable("settings") { MenuTopBottomBarScreenContent("Settings Screen") }
            composable("profile") { MenuTopBottomBarScreenContent("Profile Screen") }
        }
    }
}