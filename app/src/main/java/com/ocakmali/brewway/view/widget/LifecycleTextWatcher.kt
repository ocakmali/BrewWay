package com.ocakmali.brewway.view.widget

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class LifecycleTextWatcher(private val editText: EditText,
                           private val send: (Editable) -> Unit)
    : TextWatcher, LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun attach() {
        editText.addTextChangedListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun detach() {
        editText.removeTextChangedListener(this)
    }

    fun registerLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        s?.let { send.invoke(it) }
    }
}