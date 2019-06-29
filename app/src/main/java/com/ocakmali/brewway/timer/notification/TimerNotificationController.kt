package com.ocakmali.brewway.timer.notification

import android.app.NotificationManager
import android.content.Context
import com.ocakmali.brewway.extensions.toMinuteAndSecond
import com.ocakmali.brewway.timer.service.TimerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class TimerNotificationController(private val service: TimerService,
                                  private val scope: CoroutineScope,
                                  private val context: Context) {

    private val manager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    private var timerJob: Job? = null

    fun startNotificationTimer(notificationProvider: ITimerNotificationProvider) = scope.launch {
        Timber.d("notification startted")
        val (minute, second) = service.time?.receive()?.toMinuteAndSecond() ?: Pair(0L, 0L)
        service.startForeground(
            NOTIFICATION_ID,
            notificationProvider.builder(context, formatTime(minute, second)).build()
        )
        startNotifying(minute to second, notificationProvider)
    }

    private fun formatTime(minute: Long, second: Long) = String.format("%02d:%02d", minute, second)

    private fun startNotifying(current: Pair<Long,Long>, notificationBuilder: ITimerNotificationProvider) {
        var (currentMinute, currentSecond) = current
        timerJob = scope.launch {
            service.time?.let {
                for (millis in it) {
                    val (minute, second) = millis.toMinuteAndSecond()
                    if (currentMinute != minute || currentSecond != second) {
                        currentMinute = minute
                        currentSecond = second
                        manager.notify(
                            NOTIFICATION_ID,
                            notificationBuilder.builder(
                                context, formatTime(currentMinute, currentSecond)
                            ).build()
                        )
                    }
                }
            }
        }
    }

    fun stopNotificationTimer() {
        Timber.d("notification stopped")
        service.stopForeground(true)
        timerJob?.cancel()
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}