package com.example.my_app_android.utils
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.updateLayoutParams

//view.visible()
object ViewUtils {
    fun show(view: View) {
        if (view.visibility != View.VISIBLE) view.visibility = View.VISIBLE
    }

    fun hide(view: View) {
        if (view.visibility != View.INVISIBLE) view.visibility = View.INVISIBLE
    }

    fun gone(view: View) {
        if (view.visibility != View.GONE) view.visibility = View.GONE
    }

    fun isVisible(view: View) = view.visibility == View.VISIBLE

    fun setMargin(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        val lp = view.layoutParams
        if (lp is ViewGroup.MarginLayoutParams) {
            lp.setMargins(left, top, right, bottom)
            view.layoutParams = lp
        }
    }

    fun setPadding(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        view.setPadding(left, top, right, bottom)
    }

    fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isFullyVisible(view: View): Boolean {
        if (!view.isShown) return false
        val rect = Rect()
        val visible = view.getGlobalVisibleRect(rect)
        val screen = Rect(0, 0, view.rootView.width, view.rootView.height)
        return visible && screen.contains(rect)
    }

    fun addRippleEffect(view: View) {
        val typedValue = TypedValue()
        val context = view.context
        val attr = android.R.attr.selectableItemBackground
        if (context.theme.resolveAttribute(attr, typedValue, true)) {
            view.setBackgroundResource(typedValue.resourceId)
            view.isClickable = true
            view.isFocusable = true
        }
    }

    fun setSize(view: View, widthPx: Int, heightPx: Int) {
        view.updateLayoutParams<ViewGroup.LayoutParams> {
            width = widthPx
            height = heightPx
        }
    }

    fun setWidth(view: View, widthPx: Int) {
        view.updateLayoutParams<ViewGroup.LayoutParams> { width = widthPx }
    }

    fun setHeight(view: View, heightPx: Int) {
        view.updateLayoutParams<ViewGroup.LayoutParams> { height = heightPx }
    }

    fun fadeIn(view: View, duration: Long = 300L) {
        view.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(duration).start()
        }
    }

    fun fadeOut(view: View, duration: Long = 300L) {
        view.animate()
            .alpha(0f)
            .setDuration(duration)
            .withEndAction { view.visibility = View.GONE }
            .start()
    }

    fun childCount(viewGroup: ViewGroup) = viewGroup.childCount

    fun setSafeOnClickListener(view: View, intervalMs: Long = 500L, onClick: (View) -> Unit) {
        var lastClick = 0L
        view.setOnClickListener {
            val now = System.currentTimeMillis()
            if (now - lastClick > intervalMs) {
                lastClick = now
                onClick(it)
            }
        }
    }

    fun isAttachedToWindow(view: View): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) view.isAttachedToWindow
        else view.windowToken != null

    fun dpToPx(context: Context, dp: Float): Int =
        (dp * context.resources.displayMetrics.density + 0.5f).toInt()

    fun pxToDp(context: Context, px: Int): Float =
        px / context.resources.displayMetrics.density

    fun isVisibleOnScreen(view: View): Boolean {
        if (!view.isShown) return false
        val rect = Rect()
        val visible = view.getGlobalVisibleRect(rect)
        return visible && rect.width() > 0 && rect.height() > 0
    }
}


