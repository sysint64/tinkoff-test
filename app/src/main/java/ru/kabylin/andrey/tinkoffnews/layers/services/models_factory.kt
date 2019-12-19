package ru.kabylin.andrey.tinkoffnews.layers.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1.BaseResponse
import ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1.NewsContentResponse
import ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1.NewsItemResponse
import ru.kabylin.andrey.tinkoffnews.layers.drivers.db.CacheDatabaseModel

fun createNewsItemModelFromResponse(response: BaseResponse<List<NewsItemResponse>>): List<NewsItemModel> =
    response.payload
        .sortedByDescending { it.publicationDate.milliseconds }
        .map(::createNewsItemModelFromNewsItemResponse)

fun createNewsItemModelFromNewsItemResponse(response: NewsItemResponse): NewsItemModel =
    NewsItemModel(
        ref = response.id,
        title = response.text
    )

fun createNewsContentModelFromResponse(response: BaseResponse<NewsContentResponse>): NewsContentModel =
    NewsContentModel(
        title = response.payload.title.text,
        content = response.payload.content
    )

fun createNewsListResponseFromCache(cache: CacheDatabaseModel): BaseResponse<List<NewsItemResponse>> =
    fromJson(cache.value)

fun createNewsContentResponseFromCache(cache: CacheDatabaseModel): BaseResponse<NewsContentResponse> =
    fromJson(cache.value)

inline fun <reified T> fromJson(json: String): T {
    val collectionType = object : TypeToken<T>() {}.type
    return Gson().fromJson<T>(json, collectionType)
}
