package ru.kabylin.andrey.tinkoffnews.ext

import java.util.*

fun now(): Date = Calendar.getInstance().time

fun nowTime(): Long = now().time

val Date.calendar: Calendar
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar
    }

fun Date.add(value: Long = 0, units: Int = Calendar.DAY_OF_MONTH): Date {
    val calendar = this.calendar
    calendar.add(units, value.toInt())
    return calendar.time
}
