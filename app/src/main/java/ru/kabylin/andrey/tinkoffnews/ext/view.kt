package ru.kabylin.andrey.tinkoffnews.ext

import android.view.View

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
