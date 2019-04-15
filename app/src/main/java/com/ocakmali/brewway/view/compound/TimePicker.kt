package com.ocakmali.brewway.view.compound

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.Spanned
import android.text.method.NumberKeyListener
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import com.ocakmali.brewway.R
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener
import java.util.concurrent.TimeUnit

class TimePicker @JvmOverloads constructor(context: Context,
                                           attrs: AttributeSet? = null,
                                           defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    interface OnValueChangeListener {
        fun onValueChanged(previous: Long, current: Long)
    }

    private val minuteText: AppCompatEditText
    private val secondText: AppCompatEditText

    private var _time: Long = 0
    var time: Long get() = _time
    set(value) {
        val minuteLong =  TimeUnit.MILLISECONDS.toMinutes(value)
        val secondLong = TimeUnit.MILLISECONDS.toSeconds(value) - TimeUnit.MINUTES.toSeconds(minuteLong)
        minuteText.setText(minuteLong.toString())
        secondText.setText(secondLong.toString())
        _time = value
    }

    private var isMinuteEdited = false
    private var isSecondEdited = false

    private var onValueChangeListener: TimePicker.OnValueChangeListener? = null

    init {

        LayoutInflater.from(getContext()).inflate(R.layout.time_picker, this, true)

        minuteText = findViewById(R.id.et_minute)
        secondText = findViewById(R.id.et_second)

        val focusChangedListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                updateTextView(v as TextView)
            }
        }

        minuteText.filters = arrayOf(NumberFilter())
        minuteText.setOnActionDoneClickListener {
            isMinuteEdited = true
            if (!isSecondEdited) {
                secondText.requestFocus()
            } else {
                searchFocus(it)
            }
        }
        minuteText.onFocusChangeListener = focusChangedListener

        secondText.filters = arrayOf(NumberFilter())
        secondText.setOnActionDoneClickListener {
            isSecondEdited = true
            if (!isMinuteEdited) {
                minuteText.requestFocus()
            } else {
                searchFocus(it)
            }
        }
        secondText.onFocusChangeListener = focusChangedListener
    }

    @SuppressLint("SetTextI18n")
    private fun updateTextView(textView: TextView) {
        val current = textView.text.toString()
        if (current.isEmpty()) {
            textView.text = "0"
        }
        updateTime(true)
    }

    private fun updateTime(notify: Boolean) {
        val currentTime: Long = calcCurrentTime()
        if (_time == currentTime) return
        val previous = _time
        _time = currentTime
        if (notify) {
            onValueChangeListener?.onValueChanged(previous, _time)
        }
    }

    private fun searchFocus(view: View) {
        val resultView: View? = view.focusSearch(View.FOCUS_DOWN)
        if (resultView?.requestFocus() != true) {
            view.clearFocus()
        }
    }

    private fun calcCurrentTime(): Long {
        val minuteLong = minuteText.text?.parseToLong()?.let { TimeUnit.MINUTES.toMillis(it) } ?: 0
        val secondLong = secondText.text?.parseToLong()?.let { TimeUnit.SECONDS.toMillis(it) } ?: 0
        return minuteLong + secondLong
    }

    private fun Editable.parseToLong(): Long {
        return this.toString().parseToLong()
    }

    private fun String.parseToLong(): Long {
        return try {
            this.toLong()
        } catch (e: NumberFormatException) {
            MIN_VALUE
        }
    }

    fun setOnValueChangeListener(fn: (previous: Long, current: Long) -> Unit) {
        onValueChangeListener = object : OnValueChangeListener {
            override fun onValueChanged(previous: Long, current: Long) {
                fn.invoke(previous, current)
            }
        }
    }

    private inner class NumberFilter : NumberKeyListener() {
        override fun getInputType(): Int {
            return InputType.TYPE_CLASS_NUMBER
        }

        override fun getAcceptedChars(): CharArray {
            return charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        }

        override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence {
            var filtered: CharSequence? = super.filter(source, start, end, dest, dstart, dend)
            if (filtered == null) {
                filtered = source?.subSequence(start, end)
            }

            val result = (dest?.subSequence(0, dstart).toString() + filtered
                    + dest?.subSequence(dend, dest.length))

            if ("" == result) {
                return result
            }
            val value = result.parseToLong()

            return if (value > MAX_TIME || result.length > MAX_TIME.toString().length) {
                ""
            } else {
                filtered!!
            }
        }
    }

    companion object {
        private const val MAX_TIME = 59L
        private const val MIN_VALUE = 0L
    }
}