package ru.kabylin.andrey.tinkoffnews.containers

import org.junit.Assert
import org.junit.Test
import ru.kabylin.andrey.tinkoffnews.ext.add
import ru.kabylin.andrey.tinkoffnews.ext.now
import java.util.*

class LifeTimeTest {
    @Test
    fun modelIsLeftover() {
        val model = LifeTimeModel(
            "Test",
            createdAt = now().add(-500, Calendar.MILLISECOND),
            lifeDurationInMilliseconds = 1000
        )

        Assert.assertFalse(LifeTimeModel.isLeftover(model))
    }

    @Test
    fun modelIsLeftoverExpired() {
        val model = LifeTimeModel(
            "Test",
            createdAt = now().add(-2000, Calendar.MILLISECOND),
            lifeDurationInMilliseconds = 1000
        )

        Assert.assertTrue(LifeTimeModel.isLeftover(model))
    }

    @Test
    fun lifeTimeTest() {
        val lifeTime = LifeTime.defaultHandler<String>()
        val lifeTimeInfinity = LifeTime.infinity<String>()
        val lifeTimeZero = LifeTime.zero<String>()

        Assert.assertTrue(lifeTime.isLeftover(now().add(-2000, Calendar.MILLISECOND).time))
        Assert.assertFalse(lifeTime.isLeftover(now().add(2000, Calendar.MILLISECOND).time))
        Assert.assertTrue(lifeTimeZero.isLeftover(now().add(2000, Calendar.MILLISECOND).time))
        Assert.assertFalse(lifeTimeInfinity.isLeftover(0))
    }
}
