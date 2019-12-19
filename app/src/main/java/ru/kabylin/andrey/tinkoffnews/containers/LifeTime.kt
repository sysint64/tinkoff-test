package ru.kabylin.andrey.tinkoffnews.containers

import ru.kabylin.andrey.tinkoffnews.ext.now

class LifeTime(val isLeftoverHandler: (ttl: Long) -> Boolean) {
    companion object {
        fun <T> defaultHandler(): LifeTime {
            return LifeTime(
                isLeftoverHandler = { isLeftover(it) }
            )
        }

        fun <T> infinity(): LifeTime {
            return LifeTime(
                isLeftoverHandler = { false }
            )
        }

        fun <T> zero(): LifeTime {
            return LifeTime(
                isLeftoverHandler = { true }
            )
        }
    }
}

fun isLeftover(ttl: Long): Boolean {
    return now().time > ttl
}
