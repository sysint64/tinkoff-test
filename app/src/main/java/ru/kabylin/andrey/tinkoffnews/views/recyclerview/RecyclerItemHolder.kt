package ru.kabylin.andrey.tinkoffnews.views.recyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerItemHolder<T>(
    protected val context: Context,
    protected val view: View
) : RecyclerView.ViewHolder(view) {
    abstract fun bind(data: T)

    open fun setOnItemClick(data: T, onItemClick: (data: T) -> Unit) {
        view.setOnClickListener { onItemClick.invoke(data) }
    }
}
