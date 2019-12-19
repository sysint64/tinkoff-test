package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_content

interface NewsContentEvent

class OnLoadContentEvent(val ref: String) : NewsContentEvent

class OnNotFoundError : NewsContentEvent
