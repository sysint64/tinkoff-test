package ru.kabylin.andrey.tinkoffnews.views.recyclerview

import android.content.Context
import android.view.View

class DummyRecyclerItemHolder<T>(context: Context, view: View)
    : RecyclerItemHolder<T>(context, view)
{
    override fun bind(data: T) {
        // Nothing
    }
}
