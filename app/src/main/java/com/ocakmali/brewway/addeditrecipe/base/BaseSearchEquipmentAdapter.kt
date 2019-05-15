package com.ocakmali.brewway.addeditrecipe.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ocakmali.brewway.R

abstract class BaseSearchEquipmentAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>,
                                             private val onClickCallback: ((T?) -> Unit)? = null)
    : PagedListAdapter<T, BaseSearchEquipmentAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_equipment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemName(getItem(position)))
        holder.itemView.setOnClickListener { onClickCallback?.invoke(getItem(position)) }
    }

    abstract fun itemName(item: T?): String?

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemTextView: TextView = view.findViewById(R.id.tv_item)

        fun bind(text: String?) {
            itemTextView.text = text
        }
    }
}