package com.example.my_app_android.utils
import android.util.Log

//LogUtils.d("MainActivity", "App started")
object LogUtils {
    private const val DEFAULT_TAG = "AppLog"
    fun d(tag: String = DEFAULT_TAG, msg: String) = Log.d(tag, msg)
    fun e(tag: String = DEFAULT_TAG, msg: String, ex: Throwable? = null) = Log.e(tag, msg, ex)
    fun i(tag: String = DEFAULT_TAG, msg: String) = Log.i(tag, msg)
    fun w(tag: String = DEFAULT_TAG, msg: String) = Log.w(tag, msg)
}
