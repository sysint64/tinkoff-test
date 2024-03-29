package ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1

data class BaseResponse<T>(
    val payload: T,
    val resultCode: String,
    val trackingId: String
)

data class NewsItemResponse(
    val id: String,
    val name: String,
    val text: String,
    val publicationDate: DateResponse,
    val bankInfoTypeId: Long
)

data class DateResponse(
    val milliseconds: Long
)

data class NewsContentResponse(
    val title: NewsTitleResponse,
    val content: String,
    val bankInfoTypeId: Long,
    val typeId: String,
    val creationDate: DateResponse,
    val lastModificationDate: DateResponse
)

data class NewsTitleResponse(
    val id: String,
    val name: String,
    val text: String,
    val publicationDate: DateResponse,
    val bankInfoTypeId: Long
)
