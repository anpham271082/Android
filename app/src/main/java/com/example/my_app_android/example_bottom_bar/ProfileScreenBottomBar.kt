package com.example.my_app_android.example_bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.animation.core.*
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileScreenBottomBar(navController: NavController) {
    var showAvatar by remember { mutableStateOf(false) }
    var showName by remember { mutableStateOf(false) }
    var showEmail by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showAvatar = true
        delay(600)  // để đủ thời gian avatar hoàn thành animation
        showName = true
        delay(600)
        showEmail = true
        delay(600)
        showButton = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SlideDownFadeItem(visible = showAvatar) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF47A0FF)),
                contentAlignment = Alignment.Center
            ) {
                Text("A", fontSize = 48.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SlideUpFadeItem(visible = showName) {
            Text(
                "Nguyễn Văn A",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF47A0FF)
            )
        }

        SlideLeftFadeItem(visible = showEmail) {
            Text("Email: nguyenvana@example.com", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        SlideRightFadeItem(visible = showButton) {
            Button(
                onClick = {
                    navController.navigate("edit_profile")  // Chuyển sang màn hình chỉnh sửa
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF47A0FF))
            ) {
                Text("Chỉnh sửa hồ sơ", color = Color.White)
            }
        }
    }
}

@Composable
fun SlideDownFadeItem(visible: Boolean, content: @Composable () -> Unit) {
    var internalVisible by remember { mutableStateOf(false) }
    val alpha = remember { Animatable(0f) }
    val offsetY = remember { Animatable(-50f) }

    LaunchedEffect(visible) {
        if (visible) {
            internalVisible = true
            alpha.animateTo(1f, tween(600, easing = FastOutSlowInEasing))
            offsetY.animateTo(0f, tween(600, easing = FastOutSlowInEasing))
        } else {
            alpha.snapTo(0f)
            offsetY.snapTo(-50f)
            internalVisible = false
        }
    }

    if (internalVisible) {
        Box(
            Modifier.graphicsLayer {
                this.alpha = alpha.value
                translationY = offsetY.value
            }
        ) {
            content()
        }
    }
}

@Composable
fun SlideUpFadeItem(visible: Boolean, content: @Composable () -> Unit) {
    var internalVisible by remember { mutableStateOf(false) }
    val alpha = remember { Animatable(0f) }
    val offsetY = remember { Animatable(50f) }

    LaunchedEffect(visible) {
        if (visible) {
            internalVisible = true
            alpha.animateTo(1f, tween(600, easing = FastOutSlowInEasing))
            offsetY.animateTo(0f, tween(600, easing = FastOutSlowInEasing))
        } else {
            alpha.snapTo(0f)
            offsetY.snapTo(50f)
            internalVisible = false
        }
    }

    if (internalVisible) {
        Box(
            Modifier.graphicsLayer {
                this.alpha = alpha.value
                translationY = offsetY.value
            }
        ) {
            content()
        }
    }
}

@Composable
fun SlideLeftFadeItem(visible: Boolean, content: @Composable () -> Unit) {
    var internalVisible by remember { mutableStateOf(false) }
    val alpha = remember { Animatable(0f) }
    val offsetX = remember { Animatable(-100f) }

    LaunchedEffect(visible) {
        if (visible) {
            internalVisible = true
            alpha.animateTo(1f, tween(600, easing = FastOutSlowInEasing))
            offsetX.animateTo(0f, tween(600, easing = FastOutSlowInEasing))
        } else {
            alpha.snapTo(0f)
            offsetX.snapTo(-100f)
            internalVisible = false
        }
    }

    if (internalVisible) {
        Box(
            Modifier.graphicsLayer {
                this.alpha = alpha.value
                translationX = offsetX.value
            }
        ) {
            content()
        }
    }
}

@Composable
fun SlideRightFadeItem(visible: Boolean, content: @Composable () -> Unit) {
    var internalVisible by remember { mutableStateOf(false) }
    val alpha = remember { Animatable(0f) }
    val offsetX = remember { Animatable(100f) }

    LaunchedEffect(visible) {
        if (visible) {
            internalVisible = true
            alpha.animateTo(1f, tween(600, easing = FastOutSlowInEasing))
            offsetX.animateTo(0f, tween(600, easing = FastOutSlowInEasing))
        } else {
            alpha.snapTo(0f)
            offsetX.snapTo(100f)
            internalVisible = false
        }
    }

    if (internalVisible) {
        Box(
            Modifier.graphicsLayer {
                this.alpha = alpha.value
                translationX = offsetX.value
            }
        ) {
            content()
        }
    }
}

@Composable
fun ProfileScreenNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "profile") {
        composable("profile") {
            ProfileScreenBottomBar(navController)
        }
        composable("edit_profile") {
            EditProfileScreen()
        }
    }
}

@Composable
fun EditProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Trang chỉnh sửa hồ sơ", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}