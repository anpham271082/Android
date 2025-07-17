package com.example.my_app_android.project.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

// Format timestamp (milliseconds) to a readable date string
fun Long.toFormattedDate(pattern: String = "dd MMM yyyy, HH:mm"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(this))
}

// Convert seconds to mm:ss format (e.g., 125 -> "02:05")
fun Int.toMinutesSeconds(): String {
    val minutes = this / 60
    val seconds = this % 60
    return String.format("%02d:%02d", minutes, seconds)
}

// Convert milliseconds to HH:mm:ss
fun Long.toHoursMinutesSeconds(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

// Get relative time (e.g., "5 minutes ago")
fun Long.toRelativeTime(now: Long = System.currentTimeMillis()): String {
    val diff = now - this
    val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val days = TimeUnit.MILLISECONDS.toDays(diff)

    return when {
        seconds < 60 -> "Just now"
        minutes < 60 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
        hours < 24 -> "$hours hour${if (hours > 1) "s" else ""} ago"
        days == 1L -> "Yesterday"
        days < 7 -> "$days day${if (days > 1) "s" else ""} ago"
        else -> this.toFormattedDate("dd MMM yyyy")
    }
}

// Get current time in milliseconds
fun nowMillis(): Long = System.currentTimeMillis()

// Check if timestamp is today
fun Long.isToday(): Boolean {
    val calendar = Calendar.getInstance()
    val today = calendar.get(Calendar.DAY_OF_YEAR)
    calendar.timeInMillis = this
    val day = calendar.get(Calendar.DAY_OF_YEAR)
    return day == today
}

// Get elapsed time in human-readable format (e.g., "2h 5m 3s")
fun Long.elapsedTimeString(): String {
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this)
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60

    return buildString {
        if (hours > 0) append("${hours}h ")
        if (minutes > 0) append("${minutes}m ")
        append("${remainingSeconds}s")
    }.trim()
}
//
//val timestamp = 1721227200000L
//val dateText = timestamp.toFormattedDate("EEE, MMM dd yyyy")
//println(dateText) // Output: "Thu, Jul 18 2024"
//
//val duration = 185
//println(duration.toMinutesSeconds()) // Output: "03:05"
//
//val ms = 3661000L
//println(ms.toHoursMinutesSeconds()) // Output: "01:01:01"
//
//val postTime = System.currentTimeMillis() - 2 * 60 * 60 * 1000 // 2 hours ago
//println(postTime.toRelativeTime()) // Output: "2 hours ago"
//
//val elapsed = 7532000L
//println(elapsed.elapsedTimeString()) // Output: "2h 5m 32s"
//
//val now = nowMillis()
//println(now.isToday()) // Output: true