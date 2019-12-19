package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsService
import java.lang.IllegalArgumentException
import io.reactivex.subscribers.TestSubscriber
import ru.kabylin.andrey.tinkoffnews.containers.EitherStringRes
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsItemModel

class NewsListStateMachineTest {
    @Test
    fun shouldDisplayErrorMessageOnError() {
        val newsService = mockk<NewsService>()
        every { newsService.getNewsList() } returns Single.error(IllegalArgumentException("Error"))

        val stateMachine = NewsListStateMachine(newsService)
        val subscriber = TestSubscriber<NewsListState>()

        stateMachine.onEvent(OnLoadListEvent())
            .subscribe(subscriber)

        subscriber.assertComplete()
        subscriber.assertNoErrors()
        subscriber.assertResult(
            LoadingState(),
            LoadingErrorState(EitherStringRes.string("Error"))
        )

        verify { newsService.getNewsList() }
        confirmVerified(newsService)
    }

    @Test
    fun loadSuccessfully() {
        val newsService = mockk<NewsService>()
        val data: List<NewsItemModel> = listOf(mockk(), mockk())

        every { newsService.getNewsList() } returns Single.just(data)

        val stateMachine = NewsListStateMachine(newsService)
        val subscriber = TestSubscriber<NewsListState>()

        stateMachine.onEvent(OnLoadListEvent())
            .subscribe(subscriber)

        subscriber.assertComplete()
        subscriber.assertNoErrors()
        subscriber.assertResult(
            LoadingState(),
            LoadedState(data)
        )

        verify { newsService.getNewsList() }
        confirmVerified(newsService)
    }

    @Test
    fun refreshSuccessfully() {
        val newsService = mockk<NewsService>()
        val data: List<NewsItemModel> = listOf(mockk(), mockk())
        val refreshData: List<NewsItemModel> = listOf(mockk(), mockk(), mockk())

        every { newsService.getNewsList() } returns Single.just(data)
        every { newsService.refreshNewsList() } returns Single.just(refreshData)

        val stateMachine = NewsListStateMachine(newsService)
        val subscriber = TestSubscriber<NewsListState>()

        stateMachine.onEvents(OnLoadListEvent(), OnRefreshListEvent())
            .subscribe(subscriber)

        subscriber.assertComplete()
        subscriber.assertNoErrors()
        subscriber.assertResult(
            LoadingState(),
            LoadedState(data),
            LoadedState(refreshData)
        )

        verify { newsService.getNewsList() }
        verify { newsService.refreshNewsList() }
        confirmVerified(newsService)
    }

    @Test
    fun refreshFailed() {
        val newsService = mockk<NewsService>()
        val data: List<NewsItemModel> = listOf(mockk(), mockk())

        every { newsService.getNewsList() } returns Single.just(data)
        every { newsService.refreshNewsList() } returns Single.error(IllegalArgumentException("Error"))

        val stateMachine = NewsListStateMachine(newsService)
        val subscriber = TestSubscriber<NewsListState>()

        stateMachine.onEvents(OnLoadListEvent(), OnRefreshListEvent())
            .subscribe(subscriber)

        subscriber.assertComplete()
        subscriber.assertNoErrors()
        subscriber.assertResult(
            LoadingState(),
            LoadedState(data),
            LoadingErrorState(EitherStringRes.string("Error"))
        )

        verify { newsService.getNewsList() }
        verify { newsService.refreshNewsList() }
        confirmVerified(newsService)
    }

    @Test
    fun shouldRouteToContentOnNewsItemTap() {
        val newsService = mockk<NewsService>()
        val data: List<NewsItemModel> = listOf(mockk(), mockk())

        every { newsService.getNewsList() } returns Single.just(data)

        val stateMachine = NewsListStateMachine(newsService)
        val subscriber = TestSubscriber<NewsListState>()

        stateMachine.onEvents(OnLoadListEvent(), OnNewsTapEvent("test"))
            .subscribe(subscriber)

        subscriber.assertComplete()
        subscriber.assertNoErrors()
        subscriber.assertResult(
            LoadingState(),
            LoadedState(data),
            RouteToNewsContent("test")
        )

        verify { newsService.getNewsList() }
        confirmVerified(newsService)
    }
}
