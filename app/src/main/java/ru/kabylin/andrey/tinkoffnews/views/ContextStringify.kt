package ru.kabylin.andrey.tinkoffnews.views

import android.content.Context

interface ContextStringify {
    fun toString(context: Context): String
}

class LocalizedString(private val factory: (context: Context) -> String) : ContextStringify {
    companion object {
        fun fromString(str: String): LocalizedString {
            return LocalizedString { str }
        }
    }

    override fun toString(context: Context): String =
        factory(context)
}
