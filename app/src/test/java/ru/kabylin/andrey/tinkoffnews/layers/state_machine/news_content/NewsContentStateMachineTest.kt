package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_content

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.Test
import ru.kabylin.andrey.tinkoffnews.containers.EitherStringRes
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsContentModel
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsService
import java.lang.IllegalArgumentException

class NewsContentStateMachineTest {
    @Test
    fun shouldDisplayErrorMessageOnError() {
        val newsService = mockk<NewsService>()
        every { newsService.getNewsContent() } returns Single.error(IllegalArgumentException("Error"))

        val stateMachine = NewsContentStateMachine(newsService)
        val subscriber = TestSubscriber<NewsContentState>()

        stateMachine.onEvent(OnLoadContentEvent())
            .subscribe(subscriber)

        subscriber.assertComplete()
        subscriber.assertNoErrors()
        subscriber.assertResult(
            LoadingState(),
            LoadingErrorState(EitherStringRes.string("Error"))
        )

        verify { newsService.getNewsContent() }
        confirmVerified(newsService)
    }

    @Test
    fun loadSuccessfully() {
        val newsService = mockk<NewsService>()
        val data = mockk<NewsContentModel>()
        every { newsService.getNewsContent() } returns Single.just(data)

        val stateMachine = NewsContentStateMachine(newsService)
        val subscriber = TestSubscriber<NewsContentState>()

        stateMachine.onEvent(OnLoadContentEvent())
            .subscribe(subscriber)

        subscriber.assertComplete()
        subscriber.assertNoErrors()
        subscriber.assertResult(
            LoadingState(),
            LoadedState(data)
        )

        verify { newsService.getNewsContent() }
        confirmVerified(newsService)
    }
}