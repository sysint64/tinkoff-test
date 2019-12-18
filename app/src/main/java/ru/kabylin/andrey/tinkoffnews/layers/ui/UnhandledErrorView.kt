package ru.kabylin.andrey.tinkoffnews.layers.ui

import android.view.View
import kotlinx.android.synthetic.main.activity_news_list.view.*
import kotlinx.android.synthetic.main.part_unhandled_error.view.*
import ru.kabylin.andrey.tinkoffnews.BuildConfig
import ru.kabylin.andrey.tinkoffnews.R
import ru.kabylin.andrey.tinkoffnews.ext.hideView
import ru.kabylin.andrey.tinkoffnews.ext.showView
import ru.kabylin.andrey.tinkoffnews.views.ContextStringify

fun showUnhandledErrorView(view: View, throwable: Throwable) {
    view.includeUnhandledError.showView()

    val message = when {
        throwable is ContextStringify -> throwable.toString(view.context)
        else -> view.context.getString(R.string.unexpected_error)
    }

//    if (BuildConfig.)
    view.includeUnhandledError.textViewErrorMessage.text = message
}

fun hideUnhandledErrorView(view: View) {
    view.includeUnhandledError.hideView()
}
