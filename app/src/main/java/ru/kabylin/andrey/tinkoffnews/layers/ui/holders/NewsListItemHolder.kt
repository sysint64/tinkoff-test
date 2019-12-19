package ru.kabylin.andrey.tinkoffnews.layers.ui.holders

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.item_news.view.*
import ru.kabylin.andrey.tinkoffnews.ext.setHtmlText
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsItemModel
import ru.kabylin.andrey.tinkoffnews.views.recyclerview.RecyclerItemHolder

class NewsListItemHolder(context: Context, view: View) :
    RecyclerItemHolder<NewsItemModel>(context, view)
{
    override fun bind(data: NewsItemModel) =
        with(view) {
            textViewTitle.setHtmlText(data.title)
        }
}
