package com.ocakmali.brewway.timer.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ocakmali.brewway.timer.service.TimerService

class StopBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceIntent = Intent(context, TimerService::class.java)
        val service = (peekService(context, serviceIntent) as TimerService.TimerBinder).service
        service.stop()
    }
}