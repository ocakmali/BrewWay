package com.ocakmali.brewway

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T : Any?> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (pending.compareAndSet(false, true)) {
                observer.onChanged(it)
            }
        })
    }

    override fun setValue(value: T?) {
        pending.set(false)
        super.setValue(value)
    }

    fun call() {
        value = null
    }
}