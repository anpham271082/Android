package com.example.my_app_android.project.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object TimeUtils {

    // ----------------------------
    // 📅 Date Formatting
    // ----------------------------

    fun getCurrentDate(pattern: String = "dd MMM yyyy"): String {
        return formatDate(System.currentTimeMillis(), pattern)
    }

    fun formatDate(timestamp: Long, pattern: String = "dd MMM yyyy"): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun parseDate(dateString: String, pattern: String = "dd MMM yyyy"): Date? {
        return runCatching {
            SimpleDateFormat(pattern, Locale.getDefault()).parse(dateString)
        }.getOrNull()
    }

    // ----------------------------
    // ⏱️ Duration Formatting
    // ----------------------------

    fun formatDuration(millis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun timeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val days = TimeUnit.MILLISECONDS.toDays(diff)

        return when {
            seconds < 60 -> "Just now"
            minutes < 60 -> "$minutes minute(s) ago"
            hours < 24 -> "$hours hour(s) ago"
            days < 7 -> "$days day(s) ago"
            else -> formatDate(timestamp, "dd MMM yyyy")
        }
    }

    fun isSameDay(timestamp1: Long, timestamp2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = timestamp1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = timestamp2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    // ----------------------------
    // 🔁 Time Conversion
    // ----------------------------

    fun minutesToMillis(minutes: Int): Long = TimeUnit.MINUTES.toMillis(minutes.toLong())

    fun hoursToMillis(hours: Int): Long = TimeUnit.HOURS.toMillis(hours.toLong())

    fun daysToMillis(days: Int): Long = TimeUnit.DAYS.toMillis(days.toLong())

    fun millisToMinutes(millis: Long): Long = TimeUnit.MILLISECONDS.toMinutes(millis)

    fun millisToHours(millis: Long): Long = TimeUnit.MILLISECONDS.toHours(millis)

    fun millisToDays(millis: Long): Long = TimeUnit.MILLISECONDS.toDays(millis)
}

//val today = TimeUtils.getCurrentDate("EEEE, dd MMM yyyy")
//// e.g., "Thursday, 18 Jul 2025"
//val birthday = TimeUtils.formatDate(1588368000000, "dd MMM yyyy")
//// → "02 May 2020"
//val date = TimeUtils.parseDate("12 Feb 2022")
//// → Date instance or null
//val duration = TimeUtils.formatDuration(65000)
//// → "01:05"
//val lastSeen = TimeUtils.timeAgo(System.currentTimeMillis() - 5 * 60 * 1000)
//// → "5 minute(s) ago"
//val isSame = TimeUtils.isSameDay(time1, time2)
//// → true or false
//val millis = TimeUtils.minutesToMillis(15) // → 900000
//val hours = TimeUtils.millisToHours(millis) // → 0