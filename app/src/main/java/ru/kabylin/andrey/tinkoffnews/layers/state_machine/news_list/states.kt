package ru.kabylin.andrey.tinkoffnews.layers.state_machine.news_list

import ru.kabylin.andrey.tinkoffnews.views.LocalizedString

interface NewsListState

class LoadingState : NewsListState

data class LoadingErrorState(val errorMessage: LocalizedString) : NewsListState

data class LoadedState(val items: List<NewItemUiModel>) : NewsListState
