package com.ocakmali.brewway.equipments.grinders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BasePagedListAdapter
import com.ocakmali.brewway.base.BaseViewHolder
import com.ocakmali.brewway.datamodel.GrinderView

class GrindersAdapter(private val listener: GrindersActionListener)
    : BasePagedListAdapter<GrinderView>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<GrinderView> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_equipments, parent, false)
        return GrindersViewHolder(listener, view)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<GrinderView>(){
            override fun areItemsTheSame(oldItem: GrinderView, newItem: GrinderView) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GrinderView, newItem: GrinderView) = oldItem == newItem
        }
    }
}