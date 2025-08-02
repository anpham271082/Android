package com.example.my_app_android.example_page_curl

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.*

@Composable
fun PageCurlView() {
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    var dragPoint by remember { mutableStateOf(Offset.Zero) }
    var isDragging by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .onSizeChanged { boxSize = it }
            .pointerInput(boxSize) {
                detectDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = { isDragging = false; dragPoint = Offset.Zero },
                    onDragCancel = { isDragging = false; dragPoint = Offset.Zero },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val newX = (dragPoint.x + dragAmount.x).coerceIn(0f, boxSize.width.toFloat())
                        val newY = (dragPoint.y + dragAmount.y).coerceIn(0f, boxSize.height.toFloat())
                        dragPoint = Offset(newX, newY)
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Góc phải dưới: điểm cố định (pivot) để kéo trang
            val pivot = Offset(w, h)

            // Nếu chưa kéo, để dragPoint ở ngoài màn hình (không hiển thị curl)
            val point = if (isDragging) dragPoint else Offset(w, h)

            // Tính toán vector từ pivot tới drag point
            val diff = pivot - point

            // Giới hạn kéo chỉ trong góc vuông dưới bên phải
            val maxDrag = 400f
            val limitedDiff = Offset(
                x = diff.x.coerceAtLeast(-maxDrag),
                y = diff.y.coerceAtMost(maxDrag)
            )

            val drag = pivot - limitedDiff

            // Vẽ trang chính màu trắng
            drawRect(Color.White, size = size)

            // Vẽ phần trang lật
            // Tạo Path uốn cong bằng quadratic bezier giữa drag point và pivot
            val controlPoint = Offset((drag.x + pivot.x) / 2, (drag.y + pivot.y) / 2)

            val curlPath = Path().apply {
                moveTo(pivot.x, pivot.y)
                lineTo(drag.x, pivot.y)
                quadraticBezierTo(controlPoint.x, controlPoint.y, pivot.x, drag.y)
                close()
            }

            // Vẽ bóng đổ mép giấy uốn cong
            val shadowColor = Color.Black.copy(alpha = 0.25f)
            drawPath(curlPath, color = Color.LightGray)
            drawPath(curlPath, brush = Brush.linearGradient(
                colors = listOf(Color.White.copy(alpha = 0.8f), Color.Transparent),
                start = drag,
                end = pivot
            ))
            drawPath(curlPath, shadowColor)

            // Vẽ bóng đổ ellipse để tạo hiệu ứng 3D cho trang lật
            drawOval(
                color = shadowColor,
                topLeft = Offset(drag.x - 150f, drag.y - 150f),
                size = Size(300f, 300f)
            )
        }

        Text(
            "Kéo góc phải dưới để lật trang",
            modifier = Modifier.align(Alignment.TopCenter).padding(16.dp),
            color = Color.Black
        )
    }
}