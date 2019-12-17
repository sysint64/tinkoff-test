package ru.kabylin.andrey.tinkoffnews.layers.ui

import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import ru.kabylin.andrey.tinkoffnews.R
import ru.kabylin.andrey.tinkoffnews.ext.hideView
import ru.kabylin.andrey.tinkoffnews.ext.showView
import ru.kabylin.andrey.tinkoffnews.layers.services.impl.NewsServiceImpl
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list.*
import ru.kabylin.andrey.tinkoffnews.views.StateMachineAppCompatActivity

class MainActivity : StateMachineAppCompatActivity<NewsListState, NewsStateMachineEvent>() {
    override val stateMachine
        get() = NewsStateMachine(NewsServiceImpl())

    override val initialState
        get() = LoadingState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dispatchEvent(OnLoadListEvent())

        swipeRefreshLayout.setOnRefreshListener {
            dispatchEvent(OnRefreshListEvent())
        }
    }

    override fun onStateTransition(prev: NewsListState, next: NewsListState) {
        Log.d("MainActivity", "TRANSITION from $prev to $next")

        when (next) {
            is LoadingState -> {
                progressBar.showView()
                recyclerView.hideView()
                swipeRefreshLayout.isEnabled = false
                swipeRefreshLayout.isRefreshing = false
            }
            is LoadingErrorState -> {
                progressBar.hideView()
                recyclerView.hideView()
                swipeRefreshLayout.isEnabled = true
                swipeRefreshLayout.isRefreshing = false
            }
            is LoadedState -> {
                progressBar.hideView()
                recyclerView.showView()
                swipeRefreshLayout.isEnabled = true
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onRoute(routeState: NewsListState) {
        when (routeState) {
            is RouteToNewsContent -> {
                Log.d("MainActivity", "ROUTE: Go to news content")
            }
        }
    }
}
