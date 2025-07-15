package com.example.my_app_android.example_drawer_menu1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.my_app_android.example_drawer_menu1.screens.DrawerMenu1ChatScreen
import com.example.my_app_android.example_drawer_menu1.screens.DrawerMenu1FavouriteScreen
import com.example.my_app_android.example_drawer_menu1.screens.DrawerMenu1HomeScreen
import com.example.my_app_android.example_drawer_menu1.screens.DrawerMenu1SettingScreen

@Composable
fun DrawerMenu1AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {
        Screens.values().forEach { screen ->
            composable(screen.route) {
                when (screen) {
                    Screens.HomeScreen -> DrawerMenu1HomeScreen()
                    Screens.ChatScreen -> DrawerMenu1ChatScreen()
                    Screens.FavouriteScreen -> DrawerMenu1FavouriteScreen()
                    Screens.SettingScreen -> DrawerMenu1SettingScreen()
                }
            }
        }
    }
}

