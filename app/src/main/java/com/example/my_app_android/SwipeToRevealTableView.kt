package com.example.my_app_android

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


// 1. Data holder cho swipe state per row
data class SwipeRowState(val animatable: Animatable<Float, *> = Animatable(0f))

@Composable
fun SwipeToRevealTableView() {
    var items by remember { mutableStateOf(List(20) { i -> "User $i" }.toMutableList()) }

    // Mỗi row sẽ có một SwipeRowState giữ Animatable riêng
    val swipeStates = remember { mutableStateMapOf<Int, SwipeRowState>() }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "User List",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(items, key = { index, _ -> index }) { index, item ->
                val rowState = swipeStates.getOrPut(index) { SwipeRowState() }

                SwipeableRow(
                    avatarRes = R.drawable.strasbourg,
                    title = item,
                    description = "$item is a top contributor with over 5 years of community experience.",
                    date = "Aug 6, 2025",
                    swipeState = rowState,
                    onEdit = { println("Edit row: $index") },
                    onDelete = {
                        println("Delete row: $index")
                        //items.removeAt(index)
                        //swipeStates.remove(index)
                    }
                )
            }
        }
    }
}

@Composable
fun SwipeableRow(
    avatarRes: Int,
    title: String,
    description: String,
    date: String,
    swipeState: SwipeRowState,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val actionWidthDp = 90.dp
    val density = LocalDensity.current
    val maxSwipePx = with(density) { actionWidthDp.toPx() }

    val offsetX = swipeState.animatable

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .height(110.dp)
    ) {
        // Back layer: Edit + Delete column
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFFEDEDED))
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 0.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionButtonBox(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    icon = Icons.Default.Edit,
                    label = "Edit",
                    onClick = onEdit
                )
                Spacer(modifier = Modifier.height(0.dp))
                ActionButtonBox(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.error,
                    icon = Icons.Default.Delete,
                    label = "Delete",
                    onClick = onDelete
                )
            }
        }

        // Foreground swipeable card

        Card(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offsetX.value + dragAmount).coerceIn(-maxSwipePx, 0f)
                                offsetX.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                val target = if (offsetX.value < -maxSwipePx / 2) -maxSwipePx else 0f
                                offsetX.animateTo(target, tween(250))
                            }
                        }
                    )
                },
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = avatarRes),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(58.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        maxLines = 2,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun ActionButtonBox(
    modifier: Modifier = Modifier,
    color: Color,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(color)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = label, tint = Color.White)
            Text(label, color = Color.White, style = MaterialTheme.typography.labelSmall)
        }
    }
}