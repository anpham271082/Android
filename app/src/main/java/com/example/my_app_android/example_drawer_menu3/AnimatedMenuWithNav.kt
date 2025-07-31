package com.example.my_app_android.example_drawer_menu3

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class MenuState { COLLAPSED, EXPANDED }

sealed class AppScreen(val route: String, val label: String, val icon: ImageVector) {
    object Home : AppScreen("home", "Home", Icons.Filled.Home)
    object Profile : AppScreen("profile", "Profile", Icons.Filled.Person)
    object Settings : AppScreen("settings", "Settings", Icons.Filled.Settings)
    companion object {
        val all = listOf(Home, Profile, Settings)
    }
}

@Composable
fun AnimatedMenuWithNav() {
    val navController = rememberNavController()
    var menuState by remember { mutableStateOf(MenuState.COLLAPSED) }

    val transition = updateTransition(targetState = menuState, label = "MenuTransition")

    val contentScale by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) },
        label = "Scale"
    ) { if (it == MenuState.EXPANDED) 0.85f else 1f }

    val contentOffsetX by transition.animateDp(
        transitionSpec = { tween(durationMillis = 300) },
        label = "OffsetX"
    ) { if (it == MenuState.EXPANDED) 240.dp else 0.dp }

    val cornerRadius by transition.animateDp(
        transitionSpec = { tween(durationMillis = 300) },
        label = "CornerRadius"
    ) { if (it == MenuState.EXPANDED) 16.dp else 0.dp }

    val menuOffsetX by transition.animateDp(
        transitionSpec = { tween(durationMillis = 300) },
        label = "MenuOffset"
    ) { if (it == MenuState.EXPANDED) 0.dp else (-240).dp }

    Box(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxSize()
                .offset(x = contentOffsetX)
                .scale(contentScale)
                .clip(RoundedCornerShape(cornerRadius))
                .shadow(
                    elevation = if (menuState == MenuState.EXPANDED) 12.dp else 0.dp,
                    shape = RoundedCornerShape(cornerRadius),
                    ambientColor = Color.Black.copy(alpha = 0.2f),
                    spotColor = Color.Black.copy(alpha = 0.3f)
                )
                .background(Color.White)
                .clickable(
                    enabled = menuState == MenuState.EXPANDED,
                    onClick = { menuState = MenuState.COLLAPSED }
                )
        ) {
            Scaffold(
                topBar = {
                    CustomTopBar(
                        menuState = menuState,
                        onMenuToggle = {
                            menuState =
                                if (menuState == MenuState.EXPANDED) MenuState.COLLAPSED else MenuState.EXPANDED
                        },
                        navController = navController
                    )
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = AppScreen.Home.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(AppScreen.Home.route) { HomeScreen() }
                    composable(AppScreen.Profile.route) { ProfileScreen() }
                    composable(AppScreen.Settings.route) { SettingsScreen() }
                }
            }
        }

        // 2. Overlay nền khi menu mở, để click ngoài menu đóng menu
        if (menuState == MenuState.EXPANDED) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable { menuState = MenuState.COLLAPSED }
            )
        }

        // 3. Menu bên trái (nằm trên overlay và content khi expanded)
        Box(
            Modifier
                .fillMaxHeight()
                .width(240.dp)
                .offset(x = menuOffsetX)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1976D2), Color(0xFF0D47A1))
                    )
                )
                .padding(16.dp)
        ) {
            Column {
                Text(
                    "",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                AppScreen.all.forEach { screen ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(screen.route) {
                                    // Giữ trạng thái duy nhất, tránh stack nhiều lần
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                                menuState = MenuState.COLLAPSED
                            }
                            .padding(vertical = 12.dp)
                    ) {
                        Icon(screen.icon, contentDescription = screen.label, tint = Color.White, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(12.dp))
                        Text(screen.label, color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTopBar(
    menuState: MenuState,
    onMenuToggle: () -> Unit,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: AppScreen.Home.route
    val currentLabel = AppScreen.all.firstOrNull { it.route == currentRoute }?.label ?: "Home"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1976D2))
            .padding(top = 30.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onMenuToggle,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                Icons.Filled.Menu,
                contentDescription = "Menu",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = currentLabel,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun HomeScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("🏠 Home", fontSize = 28.sp)
    }
}

@Composable
fun ProfileScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("👤 Profile", fontSize = 28.sp)
    }
}

@Composable
fun SettingsScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("⚙️ Settings", fontSize = 28.sp)
    }
}