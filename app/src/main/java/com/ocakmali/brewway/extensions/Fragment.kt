package com.ocakmali.brewway.extensions

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ocakmali.brewway.R

fun Fragment.snackbar(@StringRes resId: Int) {
    val container: View? = activity?.findViewById(R.id.container)
    container?.let {
        Snackbar.make(it, resId, Snackbar.LENGTH_SHORT).show()
    }
}

fun Fragment.toast(@StringRes resId: Int) {
    Toast.makeText(activity, resId, Toast.LENGTH_SHORT ).show()
}