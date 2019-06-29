package com.ocakmali.brewway.timer.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.ocakmali.brewway.R

abstract class AbstractTimerNotificationProvider(context: Context) : ITimerNotificationProvider {

    init {
        createTimerChannel(context)
    }

    private  fun createTimerChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.timer)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(TIMER_CHANNEL_ID, name , importance)

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object{
        @JvmStatic
        protected val TIMER_CHANNEL_ID = "TimerChannel"
    }
}