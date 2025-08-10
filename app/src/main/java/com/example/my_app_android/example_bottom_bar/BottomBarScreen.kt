package com.example.my_app_android

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.my_app_android.example_bottom_bar.CallScreenBottomBar
import com.example.my_app_android.example_bottom_bar.CartScreenBottomBar
import com.example.my_app_android.example_bottom_bar.HomeScreenBottomBar
import com.example.my_app_android.example_bottom_bar.ProfileScreenBottomBar
import com.example.my_app_android.example_bottom_bar.ProfileScreenNavGraph
import com.example.my_app_android.example_bottom_bar.SearchScreenBottomBar

@Composable
fun BottomBarScreen() {
    val selectedIndex = remember { mutableStateOf(0) }
    val screens = listOf<@Composable () -> Unit>(
        { HomeScreenBottomBar() },
        { CartScreenBottomBar() },
        { ProfileScreenNavGraph() },
        { SearchScreenBottomBar() },
        { CallScreenBottomBar() }
    )

    Scaffold(
        bottomBar = {
            BottomBar(
                selectedIndex = selectedIndex.value,
                onItemSelected = { selectedIndex.value = it }
            )
        }
    ) {_ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(bottom = 60.dp) // Đảm bảo không bị che bởi BottomBar
        ) {
            screens[selectedIndex.value].invoke()
        }
    }
}

@Composable
fun BottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val configuration = LocalConfiguration.current
    val items = remember {
        mutableStateListOf(
            Item(Icons.Rounded.Home, Color(0xFFFF4747)),
            Item(Icons.Rounded.ShoppingCart, Color(0xFFFFAC47)),
            Item(Icons.Rounded.Person, Color(0xFF47A0FF)),
            Item(Icons.Rounded.Search, Color(0xFFB247FF)),
            Item(Icons.Rounded.Call, Color(0xFF47FFA0)),
        )
    }
    val indicatorWidth = (configuration.screenWidthDp / items.count()) / 2
    val indicatorOffset by animateIntOffsetAsState(
        targetValue = IntOffset(
            items[selectedIndex].offset.x.toInt() + (items[selectedIndex].size.width / 4) - (items.count() * 2) + (-2),
            15
        ),
        animationSpec = tween(400)
    )
    val indicatorColor by animateColorAsState(
        targetValue = items[selectedIndex].color,
        animationSpec = tween(500)
    )
    val infiniteTransition = rememberInfiniteTransition()
    val indicatorFlashingColor by infiniteTransition.animateFloat(
        initialValue = .7f,
        targetValue = .6f,
        animationSpec = infiniteRepeatable(animation = tween(2000), repeatMode = RepeatMode.Reverse)
    )
    val switching = remember { mutableStateOf(false) }
    LaunchedEffect(selectedIndex) {
        switching.value = true
        delay(250)
        switching.value = false
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF212121))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .onGloballyPositioned {
                            val offset = it.positionInParent()
                            items[index] = items[index].copy(offset = offset, size = it.size)
                        }
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { onItemSelected(index) }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = if (selectedIndex == index) item.color else Color.White
                    )
                }
            }
        }

        Column(
            modifier = Modifier.offset { indicatorOffset },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .shadow(2.dp, CircleShape, ambientColor = indicatorColor, spotColor = indicatorColor)
                    .height(3.dp)
                    .width(indicatorWidth.dp)
                    .clip(CircleShape)
                    .background(indicatorColor)
            )
            AnimatedVisibility(
                visible = !switching.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .drawBehind {
                            val path = Path().apply {
                                moveTo(100f, 0f)
                                lineTo(38f, 0f)
                                lineTo(-3f, 135f)
                                lineTo(135f, 135f)
                                close()
                            }
                            drawPath(
                                path = path,
                                brush = Brush.verticalGradient(
                                    listOf(
                                        indicatorColor.copy(alpha = indicatorFlashingColor - .2f),
                                        indicatorColor.copy(alpha = indicatorFlashingColor - .4f),
                                        Color.Transparent
                                    )
                                )
                            )
                        }
                )
            }
        }
    }
}

data class Item(
    val icon: ImageVector,
    val color: Color,
    val offset: Offset = Offset(0f, 0f),
    val size: IntSize = IntSize(0, 0)
)










