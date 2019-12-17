package ru.kabylin.andrey.tinkoffnews.views

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import ru.kabylin.andrey.tinkoffnews.ext.disposeBy

interface UiState {
    val isRouteState: Boolean
        get() = false
}

abstract class StateMachineAppCompatActivity<State : UiState, Event> : AppCompatActivity() {
    abstract val stateMachine: StateMachine<State, Event>

    abstract val initialState: State

    private val stateDisposable = CompositeDisposable()

    var currentState: State = initialState
        private set

    fun dispatchEvent(event: Event, scheduler: Scheduler = Schedulers.io()) {
        stateMachine.onEvent(event)
            .onBackpressureBuffer()
            .subscribeOn(scheduler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    if (it.isRouteState) {
                        onRoute(it)
                    } else {
                        onStateTransition(currentState, it)
                        currentState = it
                    }
                }
            )
            .disposeBy(stateDisposable)
    }

    abstract fun onStateTransition(prev: State, next: State)

    abstract fun onRoute(routeState: State)
}
