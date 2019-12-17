package ru.kabylin.andrey.tinkoffnews.layers.ui.holders

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.item_news.view.*
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list.NewItemUiModel
import ru.kabylin.andrey.tinkoffnews.views.recyclerview.RecyclerItemHolder

class NewsListItemHolder(context: Context, view: View) :
    RecyclerItemHolder<NewItemUiModel>(context, view)
{
    override fun bind(data: NewItemUiModel) =
        with(view) {
            textViewTitle.text = data.title
        }
}
