package ru.kabylin.andrey.tinkoffnews.layers.services

import io.reactivex.Single
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list.NewsItemUiModel

interface NewsService {
    fun refreshNewsList(): Single<List<NewsItemUiModel>>

    fun getNewsList(): Single<List<NewsItemUiModel>>

    fun getNewsContent(): Single<NewsItemUiModel>
}
