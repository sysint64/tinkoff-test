package ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1

data class NewApiResponse(
    val id: Long,
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
