package ru.kabylin.andrey.tinkoffnews.ext

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView

fun View.showView() {
    visibility = View.VISIBLE
}

fun View.hideView() {
    visibility = View.GONE
}

fun View.invisibleView() {
    visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean =
    visibility == View.VISIBLE

fun View.isHidden(): Boolean =
    visibility != View.VISIBLE

fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun TextView.setHtmlText(html: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        text =
            Html.fromHtml(
                html,
                Html.FROM_HTML_MODE_COMPACT
            )
    } else {
        @Suppress("DEPRECATION")
        text = Html.fromHtml(html)
    }
}
