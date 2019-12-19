package ru.kabylin.andrey.tinkoffnews.layers.services.impl

import io.reactivex.Single
import ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1.NewsRepository
import ru.kabylin.andrey.tinkoffnews.layers.services.*
import java.util.concurrent.TimeUnit

class NewsServiceImpl(private val repository: NewsRepository) : NewsService {
    override fun refreshNewsList(): Single<List<NewsItemModel>> =
        repository.clear()
            .andThen(repository.getNewsList())
            .map(::createNewsItemModelFromResponse)

    override fun getNewsList(): Single<List<NewsItemModel>> =
        repository.getNewsList()
            .map(::createNewsItemModelFromResponse)

    override fun getNewsContent(ref: String): Single<NewsContentModel> =
        repository.getNewsContent(ref)
            .map(::createNewsContentModelFromResponse)
}
