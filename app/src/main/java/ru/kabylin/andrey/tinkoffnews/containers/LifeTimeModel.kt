package ru.kabylin.andrey.tinkoffnews.containers

import ru.kabylin.andrey.tinkoffnews.ext.add
import java.util.*

class LifeTimeModel<T>(
    private val model: T,
    private val createdAt: Date,
    private val lifeDurationInMilliseconds: Long
) {
    companion object {
        fun <T> of(model: T, lifeDurationInMilliseconds: Long) : LifeTimeModel<T> {
            return LifeTimeModel(
                model = model,
                createdAt = Date(),
                lifeDurationInMilliseconds = lifeDurationInMilliseconds
            )
        }

        fun <T> isLeftover(lifeTime: LifeTimeModel<T>?): Boolean {
            if (lifeTime == null) {
                return true
            }

            val expired = lifeTime.createdAt.add(
                lifeTime.lifeDurationInMilliseconds,
                Calendar.MILLISECOND
            )
            val now = Date()

            return now > expired
        }
    }
}
