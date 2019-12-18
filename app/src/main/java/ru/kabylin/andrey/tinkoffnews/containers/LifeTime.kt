package ru.kabylin.andrey.tinkoffnews.containers

class LifeTime<T>(
    val durationInMilliseconds: Long,
    val isLeftoverHandler: (LifeTimeModel<T>) -> Boolean
) {
    companion object {
        fun <T> defaultHandler(durationInMilliseconds: Long): LifeTime<T> {
            return LifeTime(
                durationInMilliseconds,
                isLeftoverHandler = { LifeTimeModel.isLeftover(it) }
            )
        }

        fun <T> infinity(): LifeTime<T> {
            return LifeTime(
                0L,
                isLeftoverHandler = { false }
            )
        }

        fun <T> zero(): LifeTime<T> {
            return LifeTime(
                0L,
                isLeftoverHandler = { true }
            )
        }
    }
}
