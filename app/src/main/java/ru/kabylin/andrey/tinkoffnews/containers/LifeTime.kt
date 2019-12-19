package ru.kabylin.andrey.tinkoffnews.containers

import ru.kabylin.andrey.tinkoffnews.ext.now

class LifeTime(val isLeftover: (ttl: Long) -> Boolean) {
    companion object {
        fun <T> defaultHandler(): LifeTime {
            return LifeTime(
                isLeftover = { isLeftover(it) }
            )
        }

        fun <T> infinity(): LifeTime {
            return LifeTime(
                isLeftover = { false }
            )
        }

        fun <T> zero(): LifeTime {
            return LifeTime(
                isLeftover = { true }
            )
        }
    }
}

fun isLeftover(ttl: Long): Boolean {
    return now().time > ttl
}
