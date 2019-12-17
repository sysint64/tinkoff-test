package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list

import io.reactivex.Flowable
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsService
import ru.kabylin.andrey.tinkoffnews.views.StateMachine
import kotlin.UnsupportedOperationException

class NewsStateMachine(private val newsService: NewsService) :
    StateMachine<NewsListState, NewsStateMachineEvent>() {
    override fun onEvent(event: NewsStateMachineEvent): Flowable<NewsListState> =
        when (event) {
            is OnLoadListEvent -> loadList()
            is OnNewsTapEvent -> onTap(event)
            is OnRefreshListEvent -> onRefresh()
            else -> throw UnsupportedOperationException("Unknown event $event")
        }

    private fun loadList(): Flowable<NewsListState> =
        produce(
            LoadingState(),
            newsService.getNewsList().map { LoadedState(it) }
        )

    private fun onTap(event: OnNewsTapEvent): Flowable<NewsListState> =
        produce(RouteToNewsContent(event.ref))

    private fun onRefresh(): Flowable<NewsListState> =
        produce(
            LoadingState(),
            newsService.refreshNewsList().map { LoadedState(it) }
        )
}
