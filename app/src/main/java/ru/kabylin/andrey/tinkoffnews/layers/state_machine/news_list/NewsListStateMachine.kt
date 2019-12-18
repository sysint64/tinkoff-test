package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list

import io.reactivex.Flowable
import ru.kabylin.andrey.tinkoffnews.containers.EitherStringRes
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsService
import ru.kabylin.andrey.tinkoffnews.views.StateMachine
import kotlin.UnsupportedOperationException

class NewsListStateMachine(private val newsService: NewsService) :
    StateMachine<NewsListState, NewsStateMachineEvent>()
{
    fun onEvents(vararg events: NewsStateMachineEvent): Flowable<NewsListState> {
        return Flowable.merge(events.map(::onEvent))
    }

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
            newsService.getNewsList()
                .map<NewsListState> { LoadedState(it) }
                .onErrorReturn {
                    LoadingErrorState(EitherStringRes.string(it.message ?: "Error"))
                }
        )

    private fun onTap(event: OnNewsTapEvent): Flowable<NewsListState> =
        produce(RouteToNewsContent(event.ref))

    private fun onRefresh(): Flowable<NewsListState> =
        produce(
            newsService.refreshNewsList()
                .map<NewsListState> { LoadedState(it) }
                .onErrorReturn {
                    LoadingErrorState(EitherStringRes.string(it.message ?: "Error"))
                }
        )
}
