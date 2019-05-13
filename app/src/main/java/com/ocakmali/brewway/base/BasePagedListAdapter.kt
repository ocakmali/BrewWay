package com.ocakmali.brewway.base

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BasePagedListAdapter<T>(diffUtil: DiffUtil.ItemCallback<T>)
    : PagedListAdapter<T, BaseViewHolder<T>>(diffUtil) {

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val obj = getItem(position) ?: return
        holder.bind(obj)
    }
}