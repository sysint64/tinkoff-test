package ru.kabylin.andrey.tinkoffnews.layers.services

data class NewsItemModel(
    val ref: String,
    val title: String
)

data class NewsContentModel(
    val title: String,
    val content: String
)
