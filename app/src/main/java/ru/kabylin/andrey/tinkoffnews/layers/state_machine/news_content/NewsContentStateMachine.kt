package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_content

import io.reactivex.Flowable
import ru.kabylin.andrey.tinkoffnews.R
import ru.kabylin.andrey.tinkoffnews.containers.EitherStringRes
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsService
import ru.kabylin.andrey.tinkoffnews.views.StateMachine
import ru.kabylin.andrey.tinkoffnews.views.produce

class NewsContentStateMachine(private val newsService: NewsService) :
    StateMachine<NewsContentState, NewsContentEvent>()
{
    override fun onEvent(event: NewsContentEvent): Flowable<NewsContentState> =
        when (event) {
            is OnLoadContentEvent -> loadContent(event)
            is OnNotFoundError -> notFoundError()
            else -> throw UnsupportedOperationException("Unknown event $event")
        }

    private fun loadContent(event: OnLoadContentEvent): Flowable<NewsContentState> =
        produce(
            LoadingState(),
            newsService.getNewsContent(event.ref)
                .map<NewsContentState> { LoadedState(it) }
                .onErrorReturn {
                    LoadingErrorState(EitherStringRes.string(it.message ?: "Error"))
                }
        )

    private fun notFoundError(): Flowable<NewsContentState> =
        produce(
            LoadingErrorState(EitherStringRes.res(R.string.not_found_error))
        )
}
