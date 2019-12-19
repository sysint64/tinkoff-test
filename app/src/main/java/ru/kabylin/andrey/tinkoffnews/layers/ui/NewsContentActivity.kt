package ru.kabylin.andrey.tinkoffnews.layers.ui

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_news_content.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import ru.kabylin.andrey.tinkoffnews.R
import ru.kabylin.andrey.tinkoffnews.ext.hideView
import ru.kabylin.andrey.tinkoffnews.ext.showView
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_content.*
import ru.kabylin.andrey.tinkoffnews.views.StateMachineAppCompatActivity

class NewsContentActivity : StateMachineAppCompatActivity<NewsContentState, NewsContentEvent>(),
    KodeinAware {
    override val kodein by kodein()
    override val stateMachine by instance<NewsContentStateMachine>()

    override val initialState: NewsContentState
        get() = LoadingState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val ref = intent.extras?.getString("ref")

        if (ref != null) {
            dispatchEvent(OnLoadContentEvent(ref))
        } else {
            dispatchEvent(OnNotFoundError())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
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
                setContent(next)
                textViewTitle.text = next.data.title
                title = next.data.title
            }
            else -> throw UnsupportedOperationException("Unknown state: $next")
        }
    }

    private fun setContent(state: LoadedState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textViewContent.text =
                Html.fromHtml(
                    state.data.content,
                    Html.FROM_HTML_MODE_COMPACT
                )
        } else {
            @Suppress("DEPRECATION")
            textViewContent.text = Html.fromHtml(state.data.content)
        }
    }
}
