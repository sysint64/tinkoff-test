package ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import ru.kabylin.andrey.tinkoffnews.containers.LifeTime
import ru.kabylin.andrey.tinkoffnews.layers.drivers.db.CacheDatabase

class NewsRepositoryImpl(
    private val retrofit: Retrofit,
    private val db: CacheDatabase
) : NewsRepository {
    private val apiGateway by lazy {
        retrofit.create(ApiGateway::class.java)
    }

    private val lifeTime = LifeTime.defaultHandler<String>()

    override fun getNewsList(): Single<BaseResponse<List<NewsItemResponse>>> {
        return apiGateway.news()
//        val cachedNews = db.dao().getValue("news")
//
//        return if (cachedNews != null) {
//            if (lifeTime.isLeftoverHandler(cachedNews.ttl)) {
//                apiGateway.news()
//            } else {
//                return try {
//                    val gson = Gson()
//                    gson.fromJson<>()
//                } catch (e: Exception) {
//                    apiGateway.news()
//                }
//            }
//        } else {
//            apiGateway.news()
//        }
    }

    override fun getNewsContent(ref: String): Single<BaseResponse<NewsContentResponse>> =
        apiGateway.newsContent(ref)

    override fun clear(): Completable =
        Completable.fromAction {
            db.dao().clear()
        }
}
