package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_content

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.Test
import ru.kabylin.andrey.tinkoffnews.R
import ru.kabylin.andrey.tinkoffnews.containers.EitherStringRes
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsContentModel
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsService
import java.lang.IllegalArgumentException

class NewsContentStateMachineTest {
    @Test
    fun shouldDisplayErrorMessageOnError() {
        val newsService = mockk<NewsService>()
        every { newsService.getNewsContent("1") } returns Single.error(IllegalArgumentException("Error"))

        val stateMachine = NewsContentStateMachine(newsService)
        val subscriber = TestSubscriber<NewsContentState>()

        stateMachine.onEvent(OnLoadContentEvent("1"))
            .subscribe(subscriber)

        subscriber.assertComplete()
        subscriber.assertNoErrors()
        subscriber.assertResult(
            LoadingState(),
            LoadingErrorState(EitherStringRes.string("Error"))
        )

        verify { newsService.getNewsContent("1") }
        confirmVerified(newsService)
    }

    @Test
    fun loadSuccessfully() {
        val newsService = mockk<NewsService>()
        val data = mockk<NewsContentModel>()
        every { newsService.getNewsContent("1") } returns Single.just(data)

        val stateMachine = NewsContentStateMachine(newsService)
        val subscriber = TestSubscriber<NewsContentState>()

        stateMachine.onEvent(OnLoadContentEvent("1"))
            .subscribe(subscriber)

        subscriber.assertComplete()
        subscriber.assertNoErrors()
        subscriber.assertResult(
            LoadingState(),
            LoadedState(data)
        )

        verify { newsService.getNewsContent("1") }
        confirmVerified(newsService)
    }

    @Test
    fun notFoundError() {
        val newsService = mockk<NewsService>()

        val stateMachine = NewsContentStateMachine(newsService)
        val subscriber = TestSubscriber<NewsContentState>()

        stateMachine.onEvent(OnNotFoundError())
            .subscribe(subscriber)

        subscriber.assertComplete()
        subscriber.assertNoErrors()
        subscriber.assertResult(
            LoadingErrorState(EitherStringRes.res(R.string.not_found_error))
        )

        confirmVerified(newsService)
    }
}