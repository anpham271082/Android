package com.example.my_app_android

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.my_app_android.example_navigation.ExampleNavigation
import com.example.my_app_android.example_navigation_arguments.ArgumentsAppNavigation
import kotlinx.coroutines.delay

@Composable
fun ButtonGrid(navController: NavController) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ROW 1
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AnimatedIconButton("MVVM 1", Icons.Default.Share, Color(0xFF4CAF50), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleMVVM1.route)
            }
            AnimatedIconButton("MVVM 2", Icons.Default.Share, Color(0xFF4CAF50), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleMVVM2.route)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ROW 2
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AnimatedIconButton("MVVM 3", Icons.Default.Share, Color(0xFF2196F3), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleMVVM3.route)
            }
            AnimatedIconButton("Hilt MVVM", Icons.Default.Share, Color(0xFF2196F3), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleHiltMVVM.route)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ROW 3
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AnimatedIconButton("Room MVVM", Icons.Default.Share, Color(0xFF607D8B), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleRoomMVVM.route)
            }
            AnimatedIconButton("Slide Image", Icons.Default.Share, Color(0xFF607D8B), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleSlideImage.route)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // ROW 4
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AnimatedIconButton("Swipe Card", Icons.Default.Share, Color(0xFF9C27B0), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleSwipeCard.route)
            }
            AnimatedIconButton("Login Register", Icons.Default.Share, Color(0xFF9C27B0), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleLoginRegister.route)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // ROW 5
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AnimatedIconButton("Menu 1", Icons.Default.Share, Color(0xFFF44336), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleMenu1.route)
            }
            AnimatedIconButton("Menu 2", Icons.Default.Share, Color(0xFFF44336), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleMenu2.route)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // ROW 6
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AnimatedIconButton("Menu 3", Icons.Default.Share, Color(0xFFFFC107), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleMenu3.route)
            }
            AnimatedIconButton("Menu Top Bottom Bar", Icons.Default.Share, Color(0xFFFFC107), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleMenuTopBottomBar.route)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // ROW 7
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AnimatedIconButton("PopBackStack", Icons.Default.Share, Color(0xFF009688), Modifier.weight(1f)) {
                navController.navigate(Screen.ExamplePopBackStack.route)
            }
            AnimatedIconButton("Navigation", Icons.Default.Share, Color(0xFF009688), Modifier.weight(1f)) {
                navController.navigate(Screen.ExampleNavigation.route)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // ROW 8
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AnimatedIconButton("Navigation Arguments", Icons.Default.Share, Color(0xFF003088), Modifier.weight(1f)) {
                navController.navigate(Screen.ArgumentsAppNavigation.route)
            }
            AnimatedIconButton("", Icons.Default.Share, Color(0xFF003088), Modifier.weight(1f) .alpha(0f)) {
                //navController.navigate(Screen.ArgumentsAppNavigation.route)
            }
        }
    }
}
@Composable
fun AnimatedIconButton(
    text: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }

    Button(
        onClick = {
            clicked = true
            onClick()
        },
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .animateContentSize()
            .height(60.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.White, fontSize = 14.sp)
    }

    LaunchedEffect(clicked) {
        if (clicked) {
            delay(100)
            clicked = false
        }
    }
}