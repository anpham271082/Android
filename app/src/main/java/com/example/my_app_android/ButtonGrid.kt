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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

data class GridButton(
    val text: String,
    val icon: ImageVector = Icons.Default.Share,
    val color: Color,
    val route: String,
    val visible: Boolean = true
)

@Composable
fun ButtonGrid(navController: NavController) {
    val buttonList = listOf(
        //GridButton("MVVM 1", color = Color(0xFF4CAF50), route = Screen.ExampleMVVM1.route),
        //GridButton("MVVM 2", color = Color(0xFF4CAF50), route = Screen.ExampleMVVM2.route),
        GridButton("MVVM", color = Color(0xFF2196F3), route = Screen.ExampleMVVM3.route),
        GridButton("Hilt MVVM", color = Color(0xFF2196F3), route = Screen.ExampleHiltMVVM.route),
        GridButton("Room MVVM", color = Color(0xFF607D8B), route = Screen.ExampleRoomMVVM.route),
        GridButton("Slide Image", color = Color(0xFF607D8B), route = Screen.ExampleSlideImage.route),
        GridButton("Swipe Card", color = Color(0xFF9C27B0), route = Screen.ExampleSwipeCard.route),
        GridButton("Login Register", color = Color(0xFF9C27B0), route = Screen.ExampleLoginRegister.route),
        //GridButton("Menu 1", color = Color(0xFFF44336), route = Screen.ExampleMenu1.route),
        //GridButton("Menu 2", color = Color(0xFFF44336), route = Screen.ExampleMenu2.route),
        GridButton("Menu", color = Color(0xFFFFC107), route = Screen.ExampleMenu3.route),
        //GridButton("Menu Top Bottom Bar", color = Color(0xFFFFC107), route = Screen.ExampleMenuTopBottomBar.route),
        //GridButton("PopBackStack", color = Color(0xFF009688), route = Screen.ExamplePopBackStack.route),
        //GridButton("Navigation", color = Color(0xFF009688), route = Screen.ExampleNavigation.route),
        //GridButton("Navigation Arguments", color = Color(0xFF003088), route = Screen.ArgumentsAppNavigation.route),
        GridButton("Page Flip", color = Color(0xFFFFC107), route = Screen.PageFlip.route),
        GridButton("Fragmented Image", color = Color(0xFF003088), route = Screen.FragmentedImage.route),
        GridButton("LazyColumn", color = Color(0xFF003088), route = Screen.LazyColumn.route),
        GridButton("Image Picker", color = Color(0xFF880088), route = Screen.ImagePicker.route),
        GridButton("Hidden", color = Color(0xFF880088), route = Screen.PageFlip.route, visible = false),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        buttonList.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                row.forEach { button ->
                    AnimatedIconButton(
                        text = button.text,
                        icon = button.icon,
                        color = button.color,
                        modifier = Modifier
                            .weight(1f)
                            .alpha(if (button.visible) 1f else 0f)
                    ) {
                        navController.navigate(button.route)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
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