package com.ocakmali.brewway.view.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter

abstract class DynamicArrayAdapter<T>(context: Context,
                                      resource: Int)
    : ArrayAdapter<T>(context, resource) {

    private var filter: ListFilter? = null
    protected val items: MutableList<T> = mutableListOf()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    abstract fun createView(inflater: LayoutInflater, position: Int, convertView: View?, parent: ViewGroup): View

    abstract fun resultToString(resultValue: Any?): String

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): T? {
        return items[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(inflater, position, convertView, parent)
    }


    override fun getFilter(): Filter {
        if (filter == null) {
            filter = ListFilter()
        }
        return filter!!
    }

    fun submitList(list: List<T>) {
        items.clear()
        items.addAll(list)
    }

    private inner class ListFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            constraint?.let {
                filterResults.values = items
                filterResults.count = items.size
            }
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return resultToString(resultValue)
        }
    }
}