package ru.kabylin.andrey.tinkoffnews.layers.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_news_list.*
import ru.kabylin.andrey.tinkoffnews.R
import ru.kabylin.andrey.tinkoffnews.ext.hideView
import ru.kabylin.andrey.tinkoffnews.ext.showView
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsItemModel
import ru.kabylin.andrey.tinkoffnews.layers.services.impl.NewsServiceImpl
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list.*
import ru.kabylin.andrey.tinkoffnews.layers.ui.holders.NewsListItemHolder
import ru.kabylin.andrey.tinkoffnews.views.StateMachineAppCompatActivity
import ru.kabylin.andrey.tinkoffnews.views.recyclerview.SingleItemRecyclerAdapter

class NewsListActivity : StateMachineAppCompatActivity<NewsListState, NewsListEvent>() {
    override val stateMachine
        get() = NewsListStateMachine(NewsServiceImpl())

    override val initialState
        get() = LoadingState()

    private val items = ArrayList<NewsItemModel>()

    private val recyclerAdapter by lazy {
        SingleItemRecyclerAdapter(
            this, items, R.layout.item_news,
            ::NewsListItemHolder, ::onNewsItemClick
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        dispatchEvent(OnLoadListEvent())

        swipeRefreshLayout.setOnRefreshListener {
            dispatchEvent(OnRefreshListEvent())
        }

        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun onNewsItemClick(newsItem: NewsItemModel) {
        dispatchEvent(OnNewsTapEvent(newsItem.ref))
    }

    override fun onStateTransition(prev: NewsListState, next: NewsListState) {
        Log.d("NewsListActivity", "TRANSITION from $prev to $next")

        when (next) {
            is LoadingState -> {
                progressBar.showView()
                recyclerView.hideView()
                textViewError.hideView()
                swipeRefreshLayout.isEnabled = false
                swipeRefreshLayout.isRefreshing = false
            }
            is LoadingErrorState -> {
                progressBar.hideView()
                recyclerView.hideView()
                textViewError.showView()
                textViewError.text = next.errorMessage.toString(this)
                swipeRefreshLayout.isEnabled = true
                swipeRefreshLayout.isRefreshing = false
            }
            is LoadedState -> {
                progressBar.hideView()
                recyclerView.showView()
                textViewError.hideView()
                swipeRefreshLayout.isEnabled = true
                swipeRefreshLayout.isRefreshing = false

                items.clear()
                items.addAll(next.items)
                recyclerAdapter.notifyDataSetChanged()
            }
            else -> throw UnsupportedOperationException("Unknown state: $next")
        }
    }

    override fun onRoute(routeState: NewsListState) {
        when (routeState) {
            is RouteToNewsContent -> {
                val intent = Intent(this, NewsContentActivity::class.java)
                intent.putExtra("ref", routeState.ref)
                startActivity(intent)
            }
        }
    }
}
