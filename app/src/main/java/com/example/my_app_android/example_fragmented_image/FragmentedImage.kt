package com.example.my_app_android.example_fragmented_image

import android.graphics.Bitmap
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.*
import kotlin.random.Random

class TileAnimState {
    val offsetX = Animatable(0f)
    val offsetY = Animatable(0f)
    val alpha = Animatable(0f)
    val rotation = Animatable(0f)
    val scale = Animatable(0.2f)
}

@Composable
fun FragmentedImage(
    image: Bitmap,
    rows: Int = 8,
    columns: Int = 8,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    containerWidth: Int,
    containerHeight: Int,
    onTap: () -> Unit
) {
    val tileWidth = image.width / columns
    val tileHeight = image.height / rows
    val tiles = remember { mutableStateListOf<Bitmap>() }
    val animStates = remember { mutableStateListOf<TileAnimState>() }

    val centerX = columns / 2f
    val centerY = rows / 2f

    var isAnimating by remember { mutableStateOf(false) }

    LaunchedEffect(image) {
        tiles.clear()
        animStates.clear()
        for (row in 0 until rows) {
            for (col in 0 until columns) {
                val x = col * tileWidth
                val y = row * tileHeight
                val tile = Bitmap.createBitmap(image, x, y, tileWidth, tileHeight)
                tiles.add(tile)
                animStates.add(TileAnimState())
            }
        }
    }

    LaunchedEffect(isVisible) {
        isAnimating = true
        animStates.forEachIndexed { index, state ->
            val row = index / columns
            val col = index % columns
            val dx = col - centerX
            val dy = row - centerY
            val angle = atan2(dy, dx)
            val radius = 300f

            val delayTime = ((dx * dx + dy * dy).toInt()) * 8L // delay giảm để nhanh hơn
            delay(delayTime)

            if (isVisible) {
                state.offsetX.snapTo((cos(angle) * radius))
                state.offsetY.snapTo((sin(angle) * radius))
                state.alpha.snapTo(0f)
                state.scale.snapTo(0.2f)
                state.rotation.snapTo(Random.nextInt(-360, 360).toFloat())

                launch { state.offsetX.animateTo(0f, tween(400, easing = FastOutSlowInEasing)) }
                launch { state.offsetY.animateTo(0f, tween(400, easing = FastOutSlowInEasing)) }
                launch { state.alpha.animateTo(1f, tween(400)) }
                launch { state.rotation.animateTo(0f, tween(400)) }
                launch { state.scale.animateTo(1f, tween(400)) }
            } else {
                launch { state.offsetX.animateTo((cos(angle) * radius * 1.5f), tween(450)) }
                launch { state.offsetY.animateTo((sin(angle) * radius * 1.5f), tween(450)) }
                launch { state.alpha.animateTo(0f, tween(450)) }
                launch { state.rotation.animateTo(Random.nextInt(-360, 360).toFloat(), tween(450)) }
                launch { state.scale.animateTo(0.2f, tween(450)) }
            }
        }
        // Delay chờ animation chạy hết sau tile cuối cùng
        val maxDistance = (centerX * centerX + centerY * centerY).toInt()
        delay(maxDistance * 8L + 500L) // thêm 500ms đệm
        isAnimating = false
    }

    val imageOffsetX = (containerWidth - image.width) / 2f
    val imageOffsetY = (containerHeight - image.height) / 2f

    Box(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures {
                if (!isAnimating) {
                    onTap()
                }
            }
        }
    ) {
        tiles.forEachIndexed { index, tile ->
            val row = index / columns
            val col = index % columns
            val state = animStates.getOrNull(index) ?: return@forEachIndexed

            Image(
                bitmap = tile.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .absoluteOffset {
                        IntOffset(
                            x = (imageOffsetX + col * tileWidth + state.offsetX.value).roundToInt(),
                            y = (imageOffsetY + row * tileHeight + state.offsetY.value).roundToInt()
                        )
                    }
                    .graphicsLayer {
                        scaleX = state.scale.value
                        scaleY = state.scale.value
                        rotationZ = state.rotation.value
                        alpha = state.alpha.value
                    }
            )
        }
    }
}