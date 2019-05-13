package com.ocakmali.brewway.view

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog

class TwoButtonAlertDialog @JvmOverloads constructor(context: Context,
                                                     @StringRes message: Int,
                                                     @StringRes positiveButton: Int,
                                                     @StringRes negativeButton: Int,
                                                     private val onClickListener: OnClickListener?,
                                                     @StyleRes themeResId: Int = 0)
    : AlertDialog(context, themeResId), DialogInterface.OnClickListener {

    interface OnClickListener {

        fun onClick(which: Int)

        companion object {
            val EXIT get() = -1
            val CONTINUE get() = -2
        }
    }


    init {
        setMessage(message.string())
        setButton(BUTTON_POSITIVE, positiveButton.string(), this)
        setButton(BUTTON_NEGATIVE, negativeButton.string(), this)
    }


    private fun Int.string(): CharSequence {
        return context.getString(this)
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when(which) {
            BUTTON_POSITIVE -> onClickListener?.onClick(OnClickListener.CONTINUE)
            BUTTON_NEGATIVE -> onClickListener?.onClick(OnClickListener.EXIT)
        }
    }
}