package ru.kabylin.andrey.tinkoffnews

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import ru.kabylin.andrey.tinkoffnews.layers.newsModule

class MainApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(newsModule(applicationContext))
    }
}
