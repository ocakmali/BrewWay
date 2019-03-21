package com.ocakmali.brewway.view.widget

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.lifecycle.Lifecycle

class DynamicAutoCompleteTextView @JvmOverloads constructor(context: Context,
                                                            attrs: AttributeSet? = null,
                                                            defStyleRes: Int = R.attr.autoCompleteTextViewStyle)
    : AppCompatAutoCompleteTextView(context,attrs,defStyleRes) {

    private var textWatcher: LifecycleTextWatcher? = null

    init {
        threshold = 1
    }

    fun setTextWatcherAndRegisterLifecycle(lifecycle: Lifecycle, onTextChange: (Editable) -> Unit) {
        setTextWatcher(onTextChange)
        registerLifecycle(lifecycle)
    }

    fun setTextWatcher(onTextChange: (Editable) -> Unit) {
        textWatcher = LifecycleTextWatcher(this, onTextChange)
    }

    fun registerLifecycle(lifecycle: Lifecycle) {
        textWatcher?.registerLifecycle(lifecycle)
    }
}