package ru.kabylin.andrey.tinkoffnews.layers.ui

import android.view.View
import kotlinx.android.synthetic.main.activity_news_list.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner
import java.lang.IllegalArgumentException

@RunWith(RobolectricTestRunner::class)
class UnhandledExceptionUITest {
    @Test
    fun newsListErrorTest() {
        val activity = buildActivity(NewsListActivity::class.java).setup().get()

        activity.showUnhandledException(IllegalArgumentException())
        Assert.assertEquals(View.VISIBLE, activity.includeUnhandledError.visibility)
    }

    @Test
    fun newsContentErrorTest() {
        val activity = buildActivity(NewsContentActivity::class.java).setup().get()

        activity.showUnhandledException(IllegalArgumentException())
        Assert.assertEquals(View.VISIBLE, activity.includeUnhandledError.visibility)
    }
}
