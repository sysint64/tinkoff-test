package ru.kabylin.andrey.tinkoffnews.views

import io.reactivex.Flowable
import io.reactivex.Single
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list.NewsListState

abstract class StateMachine<State, Event> {
    abstract fun onEvent(event: Event): Flowable<State>

    @Suppress("UNCHECKED_CAST")
    protected fun produce(vararg states: Any): Flowable<NewsListState> {
        val statesPublishers = ArrayList<Flowable<NewsListState>>()

        for (state in states) {
            val publisher: Flowable<NewsListState> = when (state) {
                is NewsListState -> Flowable.just(state)
                is Single<*> -> state.toFlowable() as Flowable<NewsListState>
                is Flowable<*> -> state as Flowable<NewsListState>
                else -> throw UnsupportedOperationException("Couldn't create publisher for $state")
            }

            statesPublishers.add(publisher)
        }

        return Flowable.concat(statesPublishers)
    }
}
