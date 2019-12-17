package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list

import io.reactivex.Flowable

abstract class StateMachine<State, Event> {
    abstract fun onEvent(event: Event): Flowable<State>
}

class NewsStateMachine : StateMachine<NewsStateMachineEvent, NewsListState>() {
    override fun onEvent(event: NewsListState): Flowable<NewsStateMachineEvent> {
        TODO()
    }
}
