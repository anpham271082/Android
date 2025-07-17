package com.example.my_app_android.project.utils

import android.util.Log

object Logger {

    /** Set this to false in production */
    var isDebug = true

    /** Default log tag */
    private const val DEFAULT_TAG = "APP_LOG"

    fun d(message: String, tag: String = DEFAULT_TAG) {
        if (isDebug) Log.d(tag, message)
    }

    fun i(message: String, tag: String = DEFAULT_TAG) {
        if (isDebug) Log.i(tag, message)
    }

    fun w(message: String, tag: String = DEFAULT_TAG) {
        if (isDebug) Log.w(tag, message)
    }

    fun e(message: String, tag: String = DEFAULT_TAG, throwable: Throwable? = null) {
        if (isDebug) {
            if (throwable != null) {
                Log.e(tag, message, throwable)
            } else {
                Log.e(tag, message)
            }
        }
    }

    inline fun <reified T> T.logd(message: String) {
        Logger.d(message, T::class.java.simpleName)
    }

    inline fun <reified T> T.loge(message: String, throwable: Throwable? = null) {
        Logger.e(message, T::class.java.simpleName, throwable)
    }

    fun logJson(json: String, tag: String = DEFAULT_TAG) {
        if (!isDebug) return
        val pretty = try {
            val jsonObj = org.json.JSONObject(json)
            jsonObj.toString(2)
        } catch (e: Exception) {
            json
        }
        Log.d(tag, pretty)
    }
}

//Logger.d("This is a debug log")
//Logger.i("App started")
//Logger.w("Something looks suspicious")
//Logger.e("Something went wrong", throwable)
//val responseJson = """
//    {"name": "John", "age": 30, "email": "john@example.com"}
//""".trimIndent()
//
//Logger.logJson(responseJson, tag = "API")