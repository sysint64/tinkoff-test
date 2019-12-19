package ru.kabylin.andrey.tinkoffnews.layers.ui

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_news_content.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import ru.kabylin.andrey.tinkoffnews.R
import ru.kabylin.andrey.tinkoffnews.ext.hideView
import ru.kabylin.andrey.tinkoffnews.ext.showView
import ru.kabylin.andrey.tinkoffnews.layers.services.impl.NewsServiceImpl
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_content.*
import ru.kabylin.andrey.tinkoffnews.views.StateMachine
import ru.kabylin.andrey.tinkoffnews.views.StateMachineAppCompatActivity

class NewsContentActivity : StateMachineAppCompatActivity<NewsContentState, NewsContentEvent>(),
    KodeinAware
{
    override val kodein by kodein()
    override val stateMachine by instance<NewsContentStateMachine>()

    override val initialState: NewsContentState
        get() = LoadingState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)

        val ref = intent.extras?.getString("ref")

        if (ref != null) {
            dispatchEvent(OnLoadContentEvent(ref))
        } else {
            dispatchEvent(OnNotFoundError())
        }
    }

    override fun onStateTransition(prev: NewsContentState, next: NewsContentState) {
        when (next) {
            is LoadingState -> {
                progressBar.showView()
                textViewError.hideView()
                textViewContent.hideView()
            }
            is LoadingErrorState -> {
                progressBar.hideView()
                textViewError.showView()
                textViewContent.hideView()
                textViewError.text = next.errorMessage.toString(this)
            }
            is LoadedState -> {
                progressBar.hideView()
                textViewError.hideView()
                textViewContent.showView()
                textViewContent.text = next.data.content
                title = next.data.title
            }
            else -> throw UnsupportedOperationException("Unknown state: $next")
        }
    }
}
