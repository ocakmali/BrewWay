package com.ocakmali.brewway.timer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ocakmali.brewway.timer.Timer
import com.ocakmali.brewway.timer.notification.ITimerNotificationProvider
import com.ocakmali.brewway.timer.notification.TimerNotificationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ReceiveChannel
import org.koin.core.KoinComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class TimerService : Service(), KoinComponent {

    private val coroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()
    }
    private val koinScope = getKoin().createScope("TimerService", named<TimerService>())
    private val notificationController: TimerNotificationController by koinScope.inject {
        parametersOf(this, coroutineScope, this)
    }
    private val timer by koinScope.inject<Timer> { parametersOf(coroutineScope) }
    private val binder = TimerBinder()
    var running = false
    var processId = DEFAULT_VALUE
    val time: ReceiveChannel<Long>? get() = timer.time

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind called")
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("on start called")
        processId = intent?.getLongExtra(
            ID,
            DEFAULT_VALUE
        ) ?: DEFAULT_VALUE
        Timber.d("process id: $processId")
        running = true
        start()
        return START_STICKY
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        koinScope.close()
        Timber.d("onDestroy")
        super.onDestroy()
    }

    fun start(): ReceiveChannel<Long> {
        Timber.d("start called")
        return timer.start()
    }

    fun stop() {
        timer.stop()
        running = false
        Timber.d("running: $running")
        stopSelf()
    }

    fun stopForeground() {
        notificationController.stopNotificationTimer()
    }

    fun getServiceToForeground(notificationProvider: ITimerNotificationProvider) {
        notificationController.startNotificationTimer(notificationProvider)
    }

    companion object {
        private const val ID = "process_id"
        private const val DEFAULT_VALUE = 0L
        fun intent(context: Context, id: Long = DEFAULT_VALUE): Intent {
            val intent = Intent(context, TimerService::class.java)
            intent.putExtra(ID, id)
            return intent
        }
    }

    inner class TimerBinder : Binder() {
        val service = this@TimerService
    }
}