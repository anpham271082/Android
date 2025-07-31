package com.example.my_app_android

import ExampleMVVM3PostListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.my_app_android.example_drawer_menu1.DrawerMenu1
import com.example.my_app_android.example_drawer_menu2.DrawerMenu2
import com.example.my_app_android.example_drawer_menu2.DrawerMenu2MainScreen
import com.example.my_app_android.example_drawer_menu3.AnimatedMenuWithNav
import com.example.my_app_android.example_hilt_mvvm.HiltMVVMNavGraph
import com.example.my_app_android.example_login_register.ExampleLoginRegisterNavGraph
import com.example.my_app_android.example_menu_top_bottom_bar.MenuTopBottomBarMainScreen
import com.example.my_app_android.example_mvvm1.ExampleMVVM1MainScreen
import com.example.my_app_android.example_mvvm1.ExampleMVVM1MainViewModel
import com.example.my_app_android.example_mvvm2.ui.ExampleMVVM2MainScreen
import com.example.my_app_android.example_mvvm2.viewmodel.ExampleMVVM2MainViewModel
import com.example.my_app_android.example_navigation.ExampleNavigation
import com.example.my_app_android.example_navigation_arguments.ArgumentsAppNavigation
import com.example.my_app_android.example_popbackstack.PopBackStackNavHost
import com.example.my_app_android.example_room_mvvm.ui.RoomMVVMNoteScreen
import com.example.my_app_android.example_slide_image.ExampleSlideImage
import com.example.my_app_android.example_swipe_card.SwipeCardScreen

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ExampleMVVM1MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainNavGraph()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val canNavigateBack = navController.previousBackStackEntry != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    } else {
                        Spacer(modifier = Modifier.width(0.dp))
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.ButtonGridScreen.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.ButtonGridScreen.route) {
                ButtonGrid(navController)
            }
            composable(Screen.ExampleMVVM1.route) {
                val viewModel: ExampleMVVM1MainViewModel = hiltViewModel()
                ExampleMVVM1MainScreen(viewModel)
            }
            composable(Screen.ExampleMVVM2.route) {
                val viewModel: ExampleMVVM2MainViewModel = hiltViewModel()
                ExampleMVVM2MainScreen(modifier = Modifier.padding(0.dp), viewModel)
            }
            composable(Screen.ExampleMVVM3.route) {
                ExampleMVVM3PostListScreen(paddingValues = paddingValues)
            }
            composable(Screen.ExampleHiltMVVM.route) {
                HiltMVVMNavGraph()
            }
            composable(Screen.ExampleRoomMVVM.route) {
                RoomMVVMNoteScreen()
            }
            composable(Screen.ExampleSlideImage.route) {
                ExampleSlideImage(
                    images = listOf(
                        "https://images.pexels.com/photos/1658967/pexels-photo-1658967.jpeg",
                        "https://images.pexels.com/photos/2896668/pexels-photo-2896668.jpeg",
                        "https://images.pexels.com/photos/3598176/pexels-photo-3598176.jpeg",
                        "https://images.pexels.com/photos/731423/pexels-photo-731423.jpeg",
                        "https://images.pexels.com/photos/1173777/pexels-photo-1173777.jpeg",
                        "https://images.pexels.com/photos/1179225/pexels-photo-1179225.jpeg"
                    )
                )
            }
            composable(Screen.ExampleSwipeCard.route) {
                SwipeCardScreen()
            }
            composable(Screen.ExampleLoginRegister.route) {
                ExampleLoginRegisterNavGraph(paddingValues)
            }
            composable(Screen.ExampleMenu1.route) {
                DrawerMenu1()
            }
            composable(Screen.ExampleMenu2.route) {
                DrawerMenu2MainScreen()
            }
            composable(Screen.ExampleMenu3.route) {
                AnimatedMenuWithNav()
            }
            composable(Screen.ExampleMenuTopBottomBar.route) {
                MenuTopBottomBarMainScreen()
            }
            composable(Screen.ExamplePopBackStack.route) {
                PopBackStackNavHost()
            }
            composable(Screen.ExampleNavigation.route) {
                ExampleNavigation()
            }
            composable(Screen.ArgumentsAppNavigation.route) {
                ArgumentsAppNavigation()
            }
        }
    }
}
sealed class Screen(val route: String) {
    object ButtonGridScreen : Screen("button_grid")
    object ExampleMVVM1 : Screen("example_mvvm1")
    object ExampleMVVM2 : Screen("example_mvvm2")
    object ExampleMVVM3 : Screen("example_mvvm3")
    object ExampleHiltMVVM : Screen("example_hilt_mvvm")
    object ExampleRoomMVVM : Screen("example_room_mvvm")
    object ExampleSlideImage : Screen("example_slide_image")
    object ExampleSwipeCard : Screen("example_swipe_card")
    object ExampleLoginRegister : Screen("example_login_register")
    object ExampleMenu1 : Screen("example_menu1")
    object ExampleMenu2 : Screen("example_menu2")
    object ExampleMenu3 : Screen("example_menu3")
    object ExampleMenuTopBottomBar : Screen("example_menu_top_bottom_bar")
    object ExamplePopBackStack : Screen("example_pop_back_tack")
    object ExampleNavigation : Screen("example_navigation")
    object ArgumentsAppNavigation : Screen("arguments_app_navigation")
}