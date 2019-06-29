package com.ocakmali.brewway.timer

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.*
import com.ocakmali.brewway.extensions.toMinuteSecondAndMillis
import com.ocakmali.brewway.timer.notification.ITimerNotificationProvider
import com.ocakmali.brewway.timer.service.TimerService
import com.ocakmali.common.DispatchersProvider
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TimerController(val id: Long,
                      private val context: Context,
                      lifecycleOwner: LifecycleOwner,
                      private val notificationProvider: ITimerNotificationProvider,
                      dispatchers: DispatchersProvider) : LifecycleObserver {

    private val scope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = dispatchers.ui + SupervisorJob()
    }
    private val connection = TimerServiceConnection()
    private val serviceIntent = TimerService.intent(context)
    val service get() = connection.binder?.service
    val time = MutableLiveData<String>()
    var timeJob: Job? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected fun onStart() {
        bindService()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected fun onStop() {
        getServiceToForeground()
        unbindService()
        timeJob?.cancel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun onDestroy() {
        scope.cancel()
    }

    private fun getServiceToForeground() {
        service?.getServiceToForeground(notificationProvider)
    }

    fun stop() {
        stopService()
    }

    fun startServiceIfNotStarted(): Boolean {
        if (service?.running == false) {
            context.startService(serviceIntent)
            publishTimeToView()
            return true
        }

        return false
    }

    private fun bindService(): Boolean {
        return context.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }

    private fun stopService() {
        service?.let { service ->
            if (!service.running) {
                return
            }

            if (service.processId == id) {
                service.stop()
            }
        }
    }

    private fun unbindService() {
        context.unbindService(connection)
    }

    private fun serviceBound() {
        service?.let { service ->
            if (!service.running) {
                return
            }

            if (service.processId == id) {
                stopForegroundService()
                publishTimeToView()
            }
        }
    }

    private fun stopForegroundService() {
        service?.stopForeground()
    }

    private fun publishTimeToView() {
        timeJob = scope.launch {
            service?.time?.let {
                for (millis in it) {
                    val (minute, second, ms) = millis.toMinuteSecondAndMillis()
                    time.value = String.format("%02d:%02d.%02d", minute, second, ms / 10 )
                }
            }
        }
    }

    private inner class TimerServiceConnection : ServiceConnection {

        var binder: TimerService.TimerBinder? = null

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as TimerService.TimerBinder
            serviceBound()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }
}