package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list

import ru.kabylin.andrey.tinkoffnews.views.LocalizedString
import ru.kabylin.andrey.tinkoffnews.views.UiState

interface NewsListState : UiState

interface NewsRouteState : NewsListState {
    override val isRouteState: Boolean
        get() = true
}

class LoadingState : NewsListState

data class LoadingErrorState(val errorMessage: LocalizedString) : NewsListState

data class LoadedState(val items: List<NewItemUiModel>) : NewsListState

data class RouteToNewsContent(val ref: String) : NewsRouteState
