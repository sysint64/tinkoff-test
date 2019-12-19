package ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1

import io.reactivex.Completable
import io.reactivex.Single

interface NewsRepository {
    fun getNewsList(): Single<List<NewsItemResponse>>

    fun getNewsContent(ref: String): Single<NewsContentResponse>

    fun clear(): Completable
}
