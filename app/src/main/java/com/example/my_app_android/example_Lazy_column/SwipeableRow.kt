package com.example.my_app_android.example_Lazy_column

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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

    // ✨ Tính tiến độ để scale/alpha cho nút Edit/Delete
    val revealProgress by remember {
        derivedStateOf {
            (-offsetX.value / maxSwipePx).coerceIn(0f, 1f)
        }
    }

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
                        .fillMaxWidth()
                        .graphicsLayer {
                            val eased = FastOutSlowInEasing.transform(revealProgress)
                            scaleX = eased
                            scaleY = eased
                            alpha = revealProgress
                        },
                    color = MaterialTheme.colorScheme.primary,
                    icon = Icons.Default.Edit,
                    label = "Edit",
                    onClick = onEdit
                )
                Spacer(modifier = Modifier.height(0.dp))
                ActionButtonBox(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .graphicsLayer {
                            val eased = FastOutSlowInEasing.transform(revealProgress)
                            scaleX = eased
                            scaleY = eased
                            alpha = revealProgress
                        },
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