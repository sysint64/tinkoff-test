package ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1

import com.google.gson.Gson
import io.reactivex.Completable
import retrofit2.Retrofit
import ru.kabylin.andrey.tinkoffnews.containers.LifeTime
import ru.kabylin.andrey.tinkoffnews.ext.add
import ru.kabylin.andrey.tinkoffnews.ext.flatMapPassCurrentCompletable
import ru.kabylin.andrey.tinkoffnews.ext.now
import ru.kabylin.andrey.tinkoffnews.layers.drivers.db.CacheDatabase
import ru.kabylin.andrey.tinkoffnews.layers.drivers.db.CacheDatabaseModel
import ru.kabylin.andrey.tinkoffnews.layers.services.createNewsContentResponseFromCache
import ru.kabylin.andrey.tinkoffnews.layers.services.createNewsListResponseFromCache
import java.util.*

class NewsRepositoryImpl(
    private val retrofit: Retrofit,
    private val db: CacheDatabase
) : NewsRepository {

    private val apiGateway by lazy { retrofit.create(ApiGateway::class.java) }
    private val lifeTime = LifeTime.defaultHandler<String>()
    private val gson by lazy { Gson() }

    override fun getNewsList() =
        db.dao().getValue("news")
            .filter { !lifeTime.isLeftover(it.ttl) }
            .map(::createNewsListResponseFromCache)
            .switchIfEmpty(getNewsAndCache())

    private fun getNewsAndCache() =
        apiGateway.news()
            .flatMapPassCurrentCompletable { cacheNews(it) }

    override fun getNewsContent(ref: String) =
        db.dao().getValue("news:$ref")
            .filter { !lifeTime.isLeftover(it.ttl) }
            .map(::createNewsContentResponseFromCache)
            .switchIfEmpty(getNewsContentAndCache(ref))

    private fun getNewsContentAndCache(ref: String) =
        apiGateway.newsContent(ref)
            .flatMapPassCurrentCompletable { cacheNewsContent(it) }

    override fun clear() =
        db.dao().clear()

    private fun cacheNews(response: BaseResponse<List<NewsItemResponse>>): Completable {
        val json = gson.toJson(response)

        return db.dao().insertValue(
            CacheDatabaseModel(
                key = "news",
                value = json,
                ttl = now().add(1, Calendar.HOUR).time
            )
        )
    }

    private fun cacheNewsContent(response: BaseResponse<NewsContentResponse>): Completable {
        val json = gson.toJson(response)

        return db.dao().insertValue(
            CacheDatabaseModel(
                key = "news:${response.payload.title.id}",
                value = json,
                ttl = now().add(1, Calendar.HOUR).time
            )
        )
    }
}
