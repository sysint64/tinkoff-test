package ru.kabylin.andrey.tinkoffnews.layers.services

import io.reactivex.Single
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list.NewItemUiModel

interface NewsService {
    fun refreshNewsList(): Single<List<NewItemUiModel>>

    fun getNewsList(): Single<List<NewItemUiModel>>

    fun getNewsContent(): Single<NewItemUiModel>
}
