package com.example.my_app_android.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {
    fun now(format: String = "yyyy-MM-dd HH:mm:ss"): String {
        return SimpleDateFormat(format, Locale.getDefault()).format(Date())
    }
    fun today(): Date = Calendar.getInstance().time

    fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
    fun isToday(date: Date): Boolean = isSameDay(date, Date())

    fun getCurrentDate(format: String = "yyyy-MM-dd HH:mm:ss"): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date())
    }

    fun daysBetweenDates(startDate: String, endDate: String, format: String = "yyyy-MM-dd"): Long? {
        return try {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            val start = sdf.parse(startDate)
            val end = sdf.parse(endDate)
            val diff = end!!.time - start!!.time
            TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        } catch (e: Exception) {
            null
        }
    }

    fun formatDate(date: Date, format: String = "yyyy-MM-dd HH:mm:ss"): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }

   fun getTimeDifference(from: Date, to: Date): String {
        val diff = to.time - from.time
        val days = TimeUnit.MILLISECONDS.toDays(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60
        return "${days}d ${hours}h ${minutes}m ${seconds}s"
    }
    fun parseDate(dateString: String, format: String): Date? {
        return try {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            sdf.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    fun convertDateFormat(dateStr: String, fromFormat: String, toFormat: String): String? {
        return try {
            val sdfFrom = SimpleDateFormat(fromFormat, Locale.getDefault())
            val sdfTo = SimpleDateFormat(toFormat, Locale.getDefault())
            val date = sdfFrom.parse(dateStr)
            sdfTo.format(date!!)
        } catch (e: Exception) {
            null
        }
    }

    fun getTomorrow(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, 1)
        return cal.time
    }
    fun isTomorrow(date: Date): Boolean {
        val cal = Calendar.getInstance().apply { time = Date() }
        cal.add(Calendar.DAY_OF_YEAR, 1)
        return isSameDay(date, cal.time)
    }

    fun getYesterday(): Date {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        return cal.time
    }
    fun isYesterday(date: Date): Boolean {
        val cal = Calendar.getInstance().apply { time = Date() }
        cal.add(Calendar.DAY_OF_YEAR, -1)
        return isSameDay(date, cal.time)
    }
}