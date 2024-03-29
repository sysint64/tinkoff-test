package ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGateway {
    @GET("/v1/news")
    fun news(): Single<BaseResponse<List<NewsItemResponse>>>

    @GET("/v1/news_content")
    fun newsContent(@Query("id") id: String): Single<BaseResponse<NewsContentResponse>>
}
