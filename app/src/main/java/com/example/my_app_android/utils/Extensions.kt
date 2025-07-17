package com.example.my_app_android.project.utils

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// -----------------------------
// 🧱 App Configuration Constants
// -----------------------------
object AppConfig {
    const val APP_NAME = "MyComposeApp"
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"
    const val DEBUG = true
    const val BASE_URL = "https://api.example.com/"
    const val API_KEY = "your_api_key_here"
}

// -----------------------------
// 🎨 UI Theming Constants
// -----------------------------
object Dimens {
    val SmallPadding = 4.dp
    val MediumPadding = 8.dp
    val LargePadding = 16.dp
    val ExtraLargePadding = 24.dp

    val IconSizeSmall = 20.dp
    val IconSizeMedium = 28.dp
    val IconSizeLarge = 36.dp

    val CornerRadius = 12.dp
}

object TextSize {
    val Small = 12.sp
    val Medium = 16.sp
    val Large = 20.sp
    val Title = 24.sp
    val Header = 30.sp
}

// -----------------------------
// 🔐 Key Identifiers (SharedPrefs, Intent, Bundle)
// -----------------------------
object Keys {
    const val USER_TOKEN = "USER_TOKEN"
    const val USER_ID = "USER_ID"
    const val THEME_MODE = "THEME_MODE"
    const val LANGUAGE = "LANGUAGE"
}

// -----------------------------
// 🧭 Navigation Routes
// -----------------------------
object Routes {
    const val HOME = "home"
    const val LOGIN = "login"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
    const val DETAILS = "details/{itemId}"
}

// -----------------------------
// 🌐 HTTP Codes / Status
// -----------------------------
object HttpCodes {
    const val OK = 200
    const val UNAUTHORIZED = 401
    const val NOT_FOUND = 404
    const val SERVER_ERROR = 500
}

// -----------------------------
// ⏱️ Timeouts & Delays
// -----------------------------
object Timeouts {
    const val SHORT_DELAY = 500L
    const val LONG_DELAY = 2000L
    const val API_TIMEOUT = 15_000L
}