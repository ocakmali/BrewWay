package com.ocakmali.brewway.extensions

import java.util.concurrent.TimeUnit

fun Long.toMinuteAndSecond(): Pair<Long, Long> {
    val minute = TimeUnit.MILLISECONDS.toMinutes(this)
    val second = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(minute)
    return minute to second
}

fun Long.toMinuteSecondAndMillis(): Triple<Long, Long, Long> {
    val (minute, second) = this.toMinuteAndSecond()
    val ms = (this - (TimeUnit.MINUTES.toMillis(minute) + TimeUnit.SECONDS.toMillis(second)))
    return Triple(minute, second, ms)
}