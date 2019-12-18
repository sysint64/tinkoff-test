package ru.kabylin.andrey.tinkoffnews.layers.ui

import android.content.Context
import android.os.Build
import android.view.View
import kotlinx.android.synthetic.main.activity_news_list.view.*
import kotlinx.android.synthetic.main.part_unhandled_error.view.*
import ru.kabylin.andrey.tinkoffnews.BuildConfig
import ru.kabylin.andrey.tinkoffnews.R
import ru.kabylin.andrey.tinkoffnews.ext.hideView
import ru.kabylin.andrey.tinkoffnews.ext.showView
import ru.kabylin.andrey.tinkoffnews.views.ContextStringify

fun getUnhandledErrorMessage(context: Context, throwable: Throwable): String {
    val unexpectedError = context.getString(R.string.unexpected_error)

    return if (BuildConfig.DEBUG) {
        "ERROR:$unexpectedError\nDIAGNOSTIC:\n" + when (throwable) {
            is ContextStringify -> throwable.toString(context)
            else -> throwable.toString()
        }
    } else {
        unexpectedError
    }
}
