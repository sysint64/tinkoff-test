package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list

interface NewsListEvent

class OnLoadListEvent : NewsListEvent

data class OnNewsTapEvent(val ref: String) : NewsListEvent

class OnRefreshListEvent : NewsListEvent

class OnUnhandledException(val throwable: Throwable) : NewsListEvent
