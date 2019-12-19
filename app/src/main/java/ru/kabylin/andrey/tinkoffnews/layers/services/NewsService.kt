package ru.kabylin.andrey.tinkoffnews.layers.services

import io.reactivex.Single

interface NewsService {
    fun refreshNewsList(): Single<List<NewsItemModel>>

    fun getNewsList(): Single<List<NewsItemModel>>

    fun getNewsContent(): Single<NewsContentModel>
}
