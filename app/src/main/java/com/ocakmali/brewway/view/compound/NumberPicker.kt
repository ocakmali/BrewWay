package com.ocakmali.brewway.view.compound

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.InputType
import android.text.Spanned
import android.text.method.NumberKeyListener
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import com.ocakmali.brewway.R
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener

@SuppressLint("ClickableViewAccessibility")
class NumberPicker @JvmOverloads constructor(context: Context,
                                             attrs: AttributeSet? = null,
                                             defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private val incrementButton: ImageButton
    private val decrementButton: ImageButton
    private val numberText: AppCompatEditText

    var minValue = 0
        set(value) {
            if (value == field) return
            if (value < 0) return
            field = value
        }
    var maxValue = Int.MAX_VALUE
    var value: Int = minValue
        set(value) {
            if (field == value) return
            var newValue = value
            if (newValue > maxValue) newValue = maxValue
            if (newValue < minValue) newValue = minValue
            field = newValue
            numberText.setText(field.toString())
        }

    private var updateValueFromLongPress: UpdateValueFromLongPress? = null
    var longPressUpdateInterval = DEFAULT_LONG_PRESS_INTERVAL

     init {

        LayoutInflater.from(getContext()).inflate(R.layout.number_picker, this, true)
        incrementButton = findViewById(R.id.btn_increment)
        decrementButton = findViewById(R.id.btn_decrement)
        numberText = findViewById(R.id.et_number)

        incrementButton.setOnClickListener {
            addToValue(1)
        }

        incrementButton.setOnLongClickListener {
            postAddToValueFromLongPress(value = 1, delay = 0)
            true
        }

        incrementButton.setOnTouchListener { _, event   ->
            if (event.action == MotionEvent.ACTION_UP) {
                updateValueFromLongPress?.let { removeCallbacks(it) }
            }
             false
        }

        decrementButton.setOnClickListener {
            addToValue(-1)
        }

        decrementButton.setOnLongClickListener {
            postAddToValueFromLongPress(value = -1, delay = 0)
            true
        }

         decrementButton.setOnTouchListener { _, event ->
             if (event.action == MotionEvent.ACTION_UP) {
                 updateValueFromLongPress?.let { removeCallbacks(it) }
             }
             false
         }

        numberText.filters = arrayOf(NumberTextFilter())
        numberText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                numberText.selectAll()
            } else {
                value = parseInt(numberText.text.toString())
            }
        }
        numberText.setOnActionDoneClickListener {
            val view: View? = it.focusSearch(View.FOCUS_DOWN)
            if (view?.requestFocus() != true) {
                it.clearFocus()
            }
        }
    }

    private fun postAddToValueFromLongPress(value: Int, delay: Long) {
        if (updateValueFromLongPress == null) {
            updateValueFromLongPress = UpdateValueFromLongPress()
        } else {
            removeCallbacks(updateValueFromLongPress)
        }
        updateValueFromLongPress!!.value = value
        postDelayed(updateValueFromLongPress, delay)
    }

    private fun addToValue(number: Int) {
        hideSoftInput()
        numberText.clearFocus()
        value = value.plus(number)
        invalidate()
    }

    private fun hideSoftInput() {
        val inputMethodManager: InputMethodManager? = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager != null && inputMethodManager.isActive(numberText)) {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    private fun parseInt(s: String): Int {
        try {
            return Integer.parseInt(s)
        } catch (e: NumberFormatException) {}
        return minValue
    }

    private inner class NumberTextFilter : NumberKeyListener() {

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
            val value = parseInt(result)

            return if (value > maxValue || result.length > maxValue.toString().length) {
                ""
            } else {
                filtered!!
            }
        }
    }

    private inner class UpdateValueFromLongPress : Runnable {
        var value: Int = 1

        override fun run() {
            addToValue(value)
            postDelayed(this, longPressUpdateInterval)
        }
    }

    companion object {
        private const val DEFAULT_LONG_PRESS_INTERVAL = 250L
    }
}