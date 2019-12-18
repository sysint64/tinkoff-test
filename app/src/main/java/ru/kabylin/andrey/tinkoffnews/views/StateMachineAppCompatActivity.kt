package ru.kabylin.andrey.tinkoffnews.views

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.part_unhandled_error.view.*
import ru.kabylin.andrey.tinkoffnews.ext.disposeBy
import ru.kabylin.andrey.tinkoffnews.ext.hideView
import ru.kabylin.andrey.tinkoffnews.ext.showView
import ru.kabylin.andrey.tinkoffnews.layers.ui.getUnhandledErrorMessage

interface UiState {
    val isCheckpointState: Boolean
        get() = false

    val isRouteState: Boolean
        get() = false
}

abstract class StateMachineAppCompatActivity<State : UiState, Event> : AppCompatActivity() {
    abstract val stateMachine: StateMachine<State, Event>

    abstract val initialState: State

    private val stateDisposable = CompositeDisposable()

    var currentState: State = initialState
        private set

    var checkpointState: State = currentState

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
                        if (it.isCheckpointState) {
                            checkpointState = it;
                        }

                        onStateTransition(currentState, it)
                        currentState = it
                    }
                },
                onError = {
                    showUnhandledException(it)
                    onStateTransition(currentState, checkpointState)
                    currentState = checkpointState
                }
            )
            .disposeBy(stateDisposable)
    }

    abstract fun onStateTransition(prev: State, next: State)

    abstract fun onRoute(routeState: State)

    open fun showUnhandledException(throwable: Throwable) {
        val errorMessage = getUnhandledErrorMessage(this, throwable)
        includeUnhandledError.showView()
        includeUnhandledError.textViewErrorMessage.text = errorMessage

        includeUnhandledError.setOnClickListener {
            includeUnhandledError.hideView()
        }
    }
}
