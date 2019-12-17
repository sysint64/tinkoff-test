package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list

import io.reactivex.Flowable
import io.reactivex.Single
import ru.kabylin.andrey.tinkoffnews.views.StateMachine
import java.util.concurrent.TimeUnit
import kotlin.UnsupportedOperationException

class NewsStateMachine : StateMachine<NewsListState, NewsStateMachineEvent>() {
    override fun onEvent(event: NewsStateMachineEvent): Flowable<NewsListState> =
        when (event) {
            is OnLoadListEvent -> loadList()
            is OnNewsTapEvent -> onTap(event)
            is OnRefreshListEvent -> onRefresh()
            else -> throw UnsupportedOperationException("Unknown event $event")
        }

    private fun serviceLoadList(): Single<String> {
        return Single.just("")
            .delay(3, TimeUnit.SECONDS)
    }

    private fun loadList(): Flowable<NewsListState> =
        produce(
            LoadingState(),
            serviceLoadList().map { LoadedState(listOf()) },
            RouteToNewsContent("ref")
        )

    private fun onTap(event: OnNewsTapEvent): Flowable<NewsListState> =
        produce(RouteToNewsContent(event.ref))

    private fun onRefresh(): Flowable<NewsListState> =
        Flowable.just<NewsListState>(LoadingState())
            .delay(1, TimeUnit.SECONDS)
            .flatMap { Flowable.just(LoadedState(listOf())) }
}
