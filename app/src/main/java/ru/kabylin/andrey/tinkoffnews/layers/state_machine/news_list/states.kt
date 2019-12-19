package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list

import ru.kabylin.andrey.tinkoffnews.containers.EitherStringRes
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsItemModel
import ru.kabylin.andrey.tinkoffnews.views.UiState

interface NewsListState : UiState

interface NewsRouteState : NewsListState {
    override val isRouteState: Boolean
        get() = true
}

data class LoadingState(private val placeholder: String = "") : NewsListState

data class LoadingErrorState(val errorMessage: EitherStringRes) : NewsListState {
    override val isCheckpointState: Boolean
        get() = true
}

data class LoadedState(val items: List<NewsItemModel>) : NewsListState {
    override val isCheckpointState: Boolean
        get() = true
}

data class RouteToNewsContent(val ref: String) : NewsRouteState
