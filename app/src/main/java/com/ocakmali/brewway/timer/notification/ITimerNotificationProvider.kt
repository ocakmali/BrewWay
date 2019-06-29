package com.ocakmali.brewway.timer.notification

import android.content.Context
import androidx.core.app.NotificationCompat

interface ITimerNotificationProvider {

    fun builder(context: Context, contentText: String): NotificationCompat.Builder
}