package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_content

import ru.kabylin.andrey.tinkoffnews.containers.EitherStringRes
import ru.kabylin.andrey.tinkoffnews.layers.services.NewsContentModel
import ru.kabylin.andrey.tinkoffnews.views.UiState

interface NewsContentState : UiState

data class LoadingState(private val placeholder: String = "") : NewsContentState

data class LoadingErrorState(val errorMessage: EitherStringRes) : NewsContentState {
    override val isCheckpointState: Boolean
        get() = true
}

data class LoadedState(val content: NewsContentModel) : NewsContentState {
    override val isCheckpointState: Boolean
        get() = true
}
