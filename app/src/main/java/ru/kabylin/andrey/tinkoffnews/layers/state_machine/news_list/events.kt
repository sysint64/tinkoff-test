package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list

interface NewsStateMachineEvent

data class OnNewsTapEvent(val id: String) : NewsStateMachineEvent

class OnRefreshListEvent : NewsStateMachineEvent

