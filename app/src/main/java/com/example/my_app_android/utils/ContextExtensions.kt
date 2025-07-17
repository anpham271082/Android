package com.example.my_app_android.project.utils

import android.Manifest
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.material.snackbar.Snackbar

/** ========== Toasts ========== **/
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, getString(resId), duration).show()
}

/** ========== Snackbar ========== **/
fun Context.snackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}
/** ========== Clipboard ========== **/
fun Context.copyToClipboard(label: String, text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    toast("Copied to clipboard")
}

/** ========== Vibrate ========== **/
@RequiresPermission(Manifest.permission.VIBRATE)
fun Context.vibrate(durationMillis: Long = 100) {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(durationMillis)
    }
}

/** ========== Intent Shortcuts ========== **/
fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

fun Context.dialPhone(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL, "tel:$phoneNumber".toUri())
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

fun Context.shareText(text: String, title: String = "Share via") {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(Intent.createChooser(intent, title))
}

fun Context.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = "package:$packageName".toUri()
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}

/** ========== Keyboard ========== **/
fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/** ========== Dimension Conversions ========== **/
fun Context.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun Context.spToPx(sp: Int): Int {
    return (sp * resources.displayMetrics.scaledDensity).toInt()
}

/** ========== Permission Utilities ========== **/
fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Activity.shouldShowPermissionRationaleCompat(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}

/** ========== Resource Access Shortcuts ========== **/
fun Context.getColorCompat(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.getStringRes(@StringRes resId: Int): String {
    return this.getString(resId)
}

/** ========== Locale & Language ========== **/
fun Context.getCurrentLanguage(): String {
    return resources.configuration.locales[0].language
}

//context.toast("Hello, World!")
//context.toast(R.string.error_message)
//context.snackbar(view, "Something went wrong")
//context.copyToClipboard("Email", "user@example.com")
//context.vibrate(200)
//context.openUrl("https://www.google.com")
//context.dialPhone("+1234567890")
//context.shareText("Check out this app!", "Share with friends")
//context.openAppSettings()
//context.hideKeyboard(editText)
//val marginPx = context.dpToPx(16)
//val textSizePx = context.spToPx(14)
//
//if (context.hasPermission(android.Manifest.permission.CAMERA)) {
//    // Permission granted
//}
//
//val color = context.getColorCompat(R.color.primary)
//val errorText = context.getStringRes(R.string.error_message)
//val language = context.getCurrentLanguage() // e.g. "en"