package com.ocakmali.brewway.timer.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.ocakmali.brewway.R
import com.ocakmali.brewway.timer.broadcastreceiver.StopBroadcastReceiver

class TimerNotificationProvider(context: Context)
    : AbstractTimerNotificationProvider(context), ITimerNotificationProvider {

    override fun builder(context: Context, contentText: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, TIMER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_timer_black)
            .setContentTitle(context.getString(R.string.timer))
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
            .setContentIntent(contentIntent(context))
            .addAction(stopAction(context))
    }

    private fun contentIntent(context: Context): PendingIntent {
        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_brew_way)
            .setDestination(R.id.dest_timer)
            .createPendingIntent()
    }

    private fun stopAction(context: Context): NotificationCompat.Action {
        val broadcastIntent = Intent(context, StopBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,
            857,
            broadcastIntent,
            PendingIntent.FLAG_ONE_SHOT)
        return NotificationCompat.Action.Builder(0,
            context.getString(R.string.stop),
            pendingIntent).build()
    }
}