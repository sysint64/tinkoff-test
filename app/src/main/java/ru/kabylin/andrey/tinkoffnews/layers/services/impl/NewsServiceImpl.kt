package ru.kabylin.andrey.tinkoffnews.layers.services.impl

import io.reactivex.Single
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsContentModel
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsItemModel
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsService
import java.util.concurrent.TimeUnit

class NewsServiceImpl : NewsService {
    override fun refreshNewsList(): Single<List<NewsItemModel>> =
        Single.just<List<NewsItemModel>>(
            listOf(
                NewsItemModel(ref = "1", title = "News 1"),
                NewsItemModel(ref = "2", title = "News 2"),
                NewsItemModel(ref = "3", title = "News 3"),
                NewsItemModel(ref = "4", title = "News 4"),
                NewsItemModel(ref = "5", title = "News 5"),
                NewsItemModel(ref = "6", title = "News 6"),
                NewsItemModel(ref = "7", title = "News 7"),
                NewsItemModel(ref = "8", title = "News 8"),
                NewsItemModel(ref = "9", title = "News 9"),
                NewsItemModel(ref = "10", title = "News 10"),
                NewsItemModel(ref = "11", title = "News 11")
            )
        )
            .delay(1, TimeUnit.SECONDS)

    override fun getNewsList(): Single<List<NewsItemModel>> =
        Single.just<List<NewsItemModel>>(
            listOf(
                NewsItemModel(ref = "1", title = "News 1"),
                NewsItemModel(ref = "2", title = "News 2"),
                NewsItemModel(ref = "3", title = "News 3"),
                NewsItemModel(ref = "4", title = "News 4"),
                NewsItemModel(ref = "5", title = "News 5"),
                NewsItemModel(ref = "6", title = "News 6"),
                NewsItemModel(ref = "7", title = "News 7"),
                NewsItemModel(ref = "8", title = "News 8"),
                NewsItemModel(ref = "9", title = "News 9"),
                NewsItemModel(ref = "10", title = "News 10")
            )
        )
            .delay(1, TimeUnit.SECONDS)

    override fun getNewsContent(ref: String): Single<NewsContentModel> =
        Single.just(
            NewsContentModel(
                title = "Test",
                content = "Test content"
            )
        )
            .delay(1, TimeUnit.SECONDS)
}
