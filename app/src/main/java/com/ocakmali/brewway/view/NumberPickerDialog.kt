package com.ocakmali.brewway.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.ocakmali.brewway.R

@SuppressLint("InflateParams")
class NumberPickerDialog @JvmOverloads constructor(context: Context,
                                                   private val numberSetListener: OnNumberSetListener?,
                                                   @StringRes title: Int? = null,
                                                   minValue: Int = 0,
                                                   value: Int = 0,
                                                   maxValue: Int = Int.MAX_VALUE,
                                                   themeResId: Int = 0)
    : AlertDialog(context, themeResId), DialogInterface.OnClickListener,
        NumberPicker.OnValueChangeListener {

    interface OnNumberSetListener {

        fun onNumberSet(view: NumberPicker, value: Int)
    }

    private val numberPicker: NumberPicker

    init {

        val dialogContext = getContext()
        val view = LayoutInflater.from(dialogContext)
                .inflate(R.layout.dialog_number_picker, null)

        setView(view)
        title?.let { setTitle(it) }
        setButton(BUTTON_POSITIVE, dialogContext.getString(R.string.done), this)
        setButton(BUTTON_NEGATIVE, dialogContext.getString(R.string.cancel), this)

        numberPicker = view.findViewById(R.id.number_picker)
        numberPicker.minValue = minValue
        numberPicker.maxValue = maxValue
        numberPicker.value = value
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            BUTTON_POSITIVE -> {
                numberPicker.clearFocus()
                numberSetListener?.onNumberSet(numberPicker, numberPicker.value)
            }

            BUTTON_NEGATIVE -> {
                cancel()
            }
        }
    }

    override fun onSaveInstanceState(): Bundle {
        val state = super.onSaveInstanceState()
        state.putInt(NUMBER, numberPicker.value)
        return state
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val number = savedInstanceState.getInt(NUMBER)
        numberPicker.value = number
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) { }

    companion object {
        private const val NUMBER: String = "number"
    }
}