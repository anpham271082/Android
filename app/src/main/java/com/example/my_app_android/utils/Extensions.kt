import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    val params = layoutParams as? ViewGroup.MarginLayoutParams
    params?.setMargins(left, top, right, bottom)
    layoutParams = params
}

//myButton.setSafeClickListener {
//    // handle click
//}
fun View.setSafeClickListener(interval: Long = 500L, action: (View) -> Unit) {
    var lastClickTime = 0L
    setOnClickListener {
        val current = System.currentTimeMillis()
        if (current - lastClickTime > interval) {
            lastClickTime = current
            action(it)
        }
    }
}

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Context.dpToPx(dp: Float): Int =
    (dp * resources.displayMetrics.density + 0.5f).toInt()

fun Context.pxToDp(px: Int): Float =
    px / resources.displayMetrics.density

//context.startActivity<MainActivity> {
//    putExtra("key", "value")
//}
inline fun <reified T : Activity> Context.startActivity(noinline block: (Intent.() -> Unit)? = null) {
    val intent = Intent(this, T::class.java)
    block?.invoke(intent)
    startActivity(intent)
}

fun String.capitalizeFirst(): String =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

fun String?.isNullOrEmptyOrBlank(): Boolean = this.isNullOrEmpty() || this.isBlank()

fun String?.orDash(): String = if (this.isNullOrBlank()) "-" else this

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


//editText.onTextChanged { text ->
//    println("User typed: $text")
//}
fun EditText.onTextChanged(onChange: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onChange(s?.toString() ?: "")
        }
    })
}

fun EditText.clearText() {
    this.setText("")
}

fun EditText.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

//imageView.loadUrl("https://example.com/image.png", placeholder = R.drawable.ic_placeholder)
fun ImageView.loadUrl(url: String?, placeholder: Int? = null, error: Int? = null) {
    if (url.isNullOrBlank()) {
        setImageResource(error ?: 0)
        return
    }
    Glide.with(this.context)
        .load(url)
        .apply {
            placeholder?.let { placeholder(it) }
            error?.let { error(it) }
        }
        .into(this)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}