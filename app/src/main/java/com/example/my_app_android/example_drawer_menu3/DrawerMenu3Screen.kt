package com.example.my_app_android.example_drawer_menu3

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
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
fun DrawerMenu3Screen() {
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
        // Các lớp layer tạo chiều sâu chỉ khi menu mở
        if (menuState == MenuState.EXPANDED) {
            // Layer thứ hai (nằm dưới cùng, lệch nhiều nhất)
            Box(
                Modifier
                    .fillMaxSize()
                    .offset(x = contentOffsetX - 38.dp, y = 36.dp)
                    .scale(contentScale - 0.1f)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(Color.Gray.copy(alpha = 0.3f))  // tăng alpha
            )
            // Layer thứ nhất (nằm trên layer 2, lệch ít hơn)
            Box(
                Modifier
                    .fillMaxSize()
                    .offset(x = contentOffsetX - 18.dp, y = 18.dp)
                    .scale(contentScale - 0.05f)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(Color.Gray.copy(alpha = 0.4f))  // tăng alpha
            )
        }

        // Main content chính
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

        // Overlay nền khi menu mở để click ngoài menu đóng menu
        if (menuState == MenuState.EXPANDED) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable { menuState = MenuState.COLLAPSED }
            )
        }

        // Menu bên trái
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
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
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
    val newsList = listOf(
        "Compose 1.4 Released!",
        "New Android Studio Arctic Fox Update",
        "Jetpack Compose Tutorial Series",
        "Understanding State in Compose",
        "Material You: What's New?"
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(newsList) { news ->
            Text(text = "• $news", fontSize = 18.sp)
        }
    }
}

// ProfileScreen với thông tin cá nhân + nút Edit
@Composable
fun ProfileScreen() {
    var isEditing by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("👤 Tên: Nguyễn Văn A", fontSize = 22.sp)
        Text("📧 Email: nguyenvana@example.com", fontSize = 18.sp)
        Text("📞 SĐT: 0123 456 789", fontSize = 18.sp)

        if (isEditing) {
            Text("Đang chỉnh sửa thông tin...", fontSize = 16.sp, color = Color.Red)
            // Bạn có thể thêm TextField để chỉnh sửa ở đây
        }

        Button(onClick = { isEditing = !isEditing }) {
            Icon(Icons.Filled.Edit, contentDescription = "Edit")
            Spacer(Modifier.width(8.dp))
            Text(if (isEditing) "Lưu" else "Chỉnh sửa")
        }
    }
}

// SettingsScreen với danh sách tùy chỉnh có icon
@Composable
fun SettingsScreen() {
    val settingsItems = listOf(
        SettingItem("Thông báo", Icons.Filled.Notifications),
        SettingItem("Quyền riêng tư", Icons.Filled.PrivacyTip),
        SettingItem("Âm thanh", Icons.Filled.VolumeUp),
        SettingItem("Wi-Fi", Icons.Filled.Wifi),
        SettingItem("Bảo mật", Icons.Filled.Security),
        SettingItem("Giới thiệu", Icons.Filled.Info),
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(settingsItems) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Xử lý click */ }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(item.icon, contentDescription = item.title, tint = Color(0xFF1976D2), modifier = Modifier.size(28.dp))
                Spacer(Modifier.width(16.dp))
                Text(item.title, fontSize = 18.sp)
            }
        }
    }
}

data class SettingItem(val title: String, val icon: ImageVector)