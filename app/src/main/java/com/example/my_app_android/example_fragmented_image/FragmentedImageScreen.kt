package com.example.my_app_android.example_fragmented_image

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.UiComposable
import androidx.core.graphics.drawable.toBitmap

fun scaleBitmapToFit(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
    val aspectRatio = bitmap.width.toFloat() / bitmap.height
    val targetRatio = targetWidth.toFloat() / targetHeight

    val (newWidth, newHeight) = if (aspectRatio > targetRatio) {
        // Rộng hơn, fit theo chiều rộng
        targetWidth to (targetWidth / aspectRatio).toInt()
    } else {
        // Cao hơn, fit theo chiều cao
        (targetHeight * aspectRatio).toInt() to targetHeight
    }

    return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
}
@Composable
@UiComposable
fun FragmentedImageScreen(imageUrl: String) {
    val context = LocalContext.current
    val density = LocalDensity.current

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showImage by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Chuyển dp -> px
        val containerWidthPx = with(density) { maxWidth.toPx().toInt() }
        val containerHeightPx = with(density) { maxHeight.toPx().toInt() }

        // Load và scale bitmap mỗi khi imageUrl hoặc kích thước thay đổi
        LaunchedEffect(imageUrl, containerWidthPx, containerHeightPx) {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .allowHardware(false)
                .build()

            val result = loader.execute(request)
            if (result is SuccessResult) {
                val original = result.drawable.toBitmap()
                bitmap = scaleBitmapToFit(original, containerWidthPx, containerHeightPx)
                showImage = true
            }
        }

        if (bitmap == null) {
            CircularProgressIndicator()
        } else {
            FragmentedImage(
                image = bitmap!!,
                rows = 8,
                columns = 8,
                isVisible = showImage,
                onTap = { showImage = !showImage },
                containerWidth = containerWidthPx,
                containerHeight = containerHeightPx,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}