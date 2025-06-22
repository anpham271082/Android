package com.example.my_app_android.utils
import android.util.Patterns
import java.text.Normalizer
import java.util.Locale
import java.util.regex.Pattern

//StringUtils.isEmailValid("test@example.com")
object StringUtils {
    fun isNullOrEmpty(str: String?): Boolean = str.isNullOrEmpty()

    fun isNullOrBlank(str: String?): Boolean = str.isNullOrBlank()

    fun capitalizeFirstChar(str: String): String =
        str.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    fun capitalizeWords(input: String): String = input.split(" ").joinToString(" ") { it.capitalize() }

    fun removeAccents(str: String): String {
        val temp = Normalizer.normalize(str, Normalizer.Form.NFD)
        return temp.replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
    }

    fun truncate(str: String, maxLength: Int): String =
        if (str.length <= maxLength) str else str.take(maxLength) + "..."

    fun removeAllWhitespace(str: String): String = str.replace("\\s+".toRegex(), "")

    fun getFirstPart(str: String, delimiter: String = " "): String =
        str.split(delimiter).firstOrNull() ?: ""

    fun repeat(str: String, times: Int): String = str.repeat(times)

    fun isEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidVietnamPhone(phone: String?): Boolean {
        if (phone.isNullOrEmpty()) return false
        val regex = "^0[3|5|7|8|9][0-9]{8}$"
        return Pattern.matches(regex, phone)
    }

    fun maskPhone(phone: String): String {
        if (phone.length < 7) return phone
        return phone.replaceRange(3, phone.length - 2, "*".repeat(phone.length - 5))
    }

    fun isPhone(phone: String): Boolean = Patterns.PHONE.matcher(phone).matches()

    fun isInteger(str: String?): Boolean = str?.toIntOrNull() != null

    fun isDouble(str: String?): Boolean = str?.toDoubleOrNull() != null

    fun isAlphabetic(str: String): Boolean = str.matches(Regex("^[a-zA-Z]+$"))

    fun isNumeric(str: String): Boolean = str.matches(Regex("^[0-9]+$"))

    fun onlyDigits(input: String): String = input.filter { it.isDigit() }
}
