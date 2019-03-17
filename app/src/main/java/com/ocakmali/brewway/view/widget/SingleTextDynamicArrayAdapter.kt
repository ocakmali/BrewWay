package com.ocakmali.brewway.view.widget

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

abstract class SingleTextDynamicArrayAdapter<T>(context: Context,
                                                private val resource: Int)
    : DynamicArrayAdapter<T>(context,resource) {

    override fun createView(inflater: LayoutInflater, position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: inflater.inflate(resource, parent, false)

        val textView: TextView = try {
            view as TextView
        } catch (e: ClassCastException) {
            Log.e("SingleTextDynamicArray", "View must be a TextView")
            throw IllegalStateException(
                    "SingleTextDynamicArrayAdapter view must be a TextView", e)
        }

        textView.text = resultToString(getItem(position))

        return view
    }
}