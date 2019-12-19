package ru.kabylin.andrey.tinkoffnews.layers.services

import ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1.BaseResponse
import ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1.NewsContentResponse
import ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1.NewsItemResponse

fun createNewsItemModelFromResponse(response: BaseResponse<List<NewsItemResponse>>): List<NewsItemModel> =
    response.payload
        .sortedBy { it.publicationDate.milliseconds }
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
