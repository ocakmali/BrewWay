package com.ocakmali.brewway.timer

import com.ocakmali.common.DispatchersProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import timber.log.Timber

class Timer(private val dispatchers: DispatchersProvider,
            scope: CoroutineScope) : CoroutineScope by scope {

    var time: ReceiveChannel<Long>? = null
    private set

    fun start(): ReceiveChannel<Long> {
        Timber.d("timer started")
        time = produceTime()
        return time!!
    }

    fun stop() {
        Timber.d( "timer stopped")
        time?.cancel()
        time = null
    }

    private fun produceTime() = produce(context = dispatchers.common,
        capacity = Channel.CONFLATED) {
        val startedTime = System.currentTimeMillis()
        while (isActive) {
            val now = System.currentTimeMillis()
            val time = now - startedTime
            send(time)
            delay(DELAY_TIME)
        }
    }

    companion object {
        private const val DELAY_TIME = 75L
    }
}