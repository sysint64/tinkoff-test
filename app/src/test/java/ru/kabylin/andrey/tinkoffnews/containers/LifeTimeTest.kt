package ru.kabylin.andrey.tinkoffnews.containers

import org.junit.Assert
import org.junit.Test
import ru.kabylin.andrey.tinkoffnews.ext.add
import ru.kabylin.andrey.tinkoffnews.ext.now
import java.util.*

class LifeTimeTest {
    @Test
    fun isLeftover() {
        val model = LifeTimeModel(
            "Test",
            createdAt = now().add(-500, Calendar.MILLISECOND),
            lifeDurationInMilliseconds = 1000
        )

        Assert.assertFalse(LifeTimeModel.isLeftover(model))
    }

    @Test
    fun isLeftoverExpired() {
        val model = LifeTimeModel(
            "Test",
            createdAt = now().add(-2000, Calendar.MILLISECOND),
            lifeDurationInMilliseconds = 1000
        )

        Assert.assertTrue(LifeTimeModel.isLeftover(model))
    }

    @Test
    fun isLeftoverConstructor() {
        val model = LifeTimeModel(
            "Test",
            createdAt = now().add(-2000, Calendar.MILLISECOND),
            lifeDurationInMilliseconds = 1000
        )

        val lifeTime = LifeTime.defaultHandler<String>(1000)
        val lifeTimeInfinity = LifeTime.infinity<String>()

        Assert.assertTrue(lifeTime.isLeftoverHandler(model))
        Assert.assertFalse(lifeTimeInfinity.isLeftoverHandler(model))
    }
}
