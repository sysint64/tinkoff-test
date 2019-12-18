package ru.kabylin.andrey.tinkoffnews.layers.services.impl

import io.reactivex.Single
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsService
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list.NewItemUiModel
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class NewsServiceImpl : NewsService {
    override fun refreshNewsList(): Single<List<NewItemUiModel>> =
        Single.just<List<NewItemUiModel>>(listOf())
            .delay(3, TimeUnit.SECONDS)
            .flatMap {
                Single.error<List<NewItemUiModel>>(IllegalArgumentException("Oh no!"))
            }

    override fun getNewsList(): Single<List<NewItemUiModel>> =
        Single.just<List<NewItemUiModel>>(listOf())
            .delay(3, TimeUnit.SECONDS)
            .flatMap {
                Single.error<List<NewItemUiModel>>(IllegalArgumentException("Oh no!"))
            }

    override fun getNewsContent(): Single<NewItemUiModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
