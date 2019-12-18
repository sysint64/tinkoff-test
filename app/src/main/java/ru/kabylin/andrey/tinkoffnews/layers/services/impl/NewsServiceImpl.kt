package ru.kabylin.andrey.tinkoffnews.layers.services.impl

import io.reactivex.Single
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsService
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list.NewsItemUiModel
import java.util.concurrent.TimeUnit

class NewsServiceImpl : NewsService {
    override fun refreshNewsList(): Single<List<NewsItemUiModel>> =
        Single.just<List<NewsItemUiModel>>(
            listOf(
                NewsItemUiModel(ref = "1", title = "News 1"),
                NewsItemUiModel(ref = "2", title = "News 2"),
                NewsItemUiModel(ref = "3", title = "News 3"),
                NewsItemUiModel(ref = "4", title = "News 4"),
                NewsItemUiModel(ref = "5", title = "News 5"),
                NewsItemUiModel(ref = "6", title = "News 6"),
                NewsItemUiModel(ref = "7", title = "News 7"),
                NewsItemUiModel(ref = "8", title = "News 8"),
                NewsItemUiModel(ref = "9", title = "News 9"),
                NewsItemUiModel(ref = "10", title = "News 10"),
                NewsItemUiModel(ref = "11", title = "News 11")
            )
        )
            .delay(1, TimeUnit.SECONDS)

    override fun getNewsList(): Single<List<NewsItemUiModel>> =
        Single.just<List<NewsItemUiModel>>(
            listOf(
                NewsItemUiModel(ref = "1", title = "News 1"),
                NewsItemUiModel(ref = "2", title = "News 2"),
                NewsItemUiModel(ref = "3", title = "News 3"),
                NewsItemUiModel(ref = "4", title = "News 4"),
                NewsItemUiModel(ref = "5", title = "News 5"),
                NewsItemUiModel(ref = "6", title = "News 6"),
                NewsItemUiModel(ref = "7", title = "News 7"),
                NewsItemUiModel(ref = "8", title = "News 8"),
                NewsItemUiModel(ref = "9", title = "News 9"),
                NewsItemUiModel(ref = "10", title = "News 10")
            )
        )
            .delay(1, TimeUnit.SECONDS)

    override fun getNewsContent(): Single<NewsItemUiModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
