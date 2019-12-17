package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list

interface NewsStateMachineEvent

class OnLoadListEvent : NewsStateMachineEvent

data class OnNewsTapEvent(val ref: String) : NewsStateMachineEvent

class OnRefreshListEvent : NewsStateMachineEvent
