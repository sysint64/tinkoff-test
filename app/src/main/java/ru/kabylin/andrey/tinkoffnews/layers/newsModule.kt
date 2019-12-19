package ru.kabylin.andrey.tinkoffnews.layers

import android.content.Context
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.kabylin.andrey.tinkoffnews.BuildConfig
import ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1.NewsRepository
import ru.kabylin.andrey.tinkoffnews.layers.drivers.api.v1.NewsRepositoryImpl
import ru.kabylin.andrey.tinkoffnews.layers.drivers.db.CacheDatabase
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsService
import ru.kabylin.andrey.tinkoffnews.layers.services.impl.NewsServiceImpl
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_content.NewsContentStateMachine
import ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list.NewsListStateMachine
import java.util.concurrent.TimeUnit

fun newsModule(context: Context) = Kodein.Module(name = "News module") {
    bind<CacheDatabase>() with provider {
        Room.databaseBuilder(context, CacheDatabase::class.java, "cache_db").build()
    }

    bind<OkHttpClient>("tinkoff_api") with provider {
        val httpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(logging)
        }

        httpClientBuilder.build()
    }

    bind<Retrofit>("tinkoff_api") with provider {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.API_ENDPOINT)
            .client(instance("tinkoff_api"))
            .build()
    }

    bind<NewsRepository>() with provider {
        NewsRepositoryImpl(
            retrofit = instance("tinkoff_api"),
            db = instance()
        )
    }

    bind<NewsService>() with singleton { NewsServiceImpl(instance()) }

    bind<NewsListStateMachine>() with singleton {
        NewsListStateMachine(instance())
    }

    bind<NewsContentStateMachine>() with singleton {
        NewsContentStateMachine(instance())
    }
}
