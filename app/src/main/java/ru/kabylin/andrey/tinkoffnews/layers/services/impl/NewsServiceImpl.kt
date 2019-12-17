package ru.kabylin.andrey.tinkoffnews.layers.services.impl

import io.reactivex.Single
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsService
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list.NewItemUiModel

class NewsServiceImpl : NewsService {
    override fun refreshNewsList(): Single<List<NewItemUiModel>> =
        Single.just(listOf())

    override fun getNewsList(): Single<List<NewItemUiModel>> =
        Single.just(listOf())

    override fun getNewsContent(): Single<NewItemUiModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}