package ru.kabylin.andrey.tinkoffnews.views

import io.reactivex.Flowable
import io.reactivex.Single

abstract class StateMachine<State, Event> {
    abstract fun onEvent(event: Event): Flowable<State>

    fun onEvents(vararg events: Event): Flowable<State> {
        return Flowable.merge(events.map(::onEvent))
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified State> produce(vararg states: Any): Flowable<State> {
    val statesPublishers = ArrayList<Flowable<State>>()

    for (state in states) {
        val publisher: Flowable<State> = when (state) {
            is State -> Flowable.just(state)
            is Single<*> -> state.toFlowable() as Flowable<State>
            is Flowable<*> -> state as Flowable<State>
            else -> throw UnsupportedOperationException("Couldn't create publisher for $state")
        }

        statesPublishers.add(publisher)
    }

    return Flowable.concat(statesPublishers)
}
