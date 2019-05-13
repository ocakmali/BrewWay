package com.ocakmali.brewway.addeditrecipe.grinder

import androidx.recyclerview.widget.DiffUtil
import com.ocakmali.brewway.addeditrecipe.base.BaseSearchEquipmentAdapter
import com.ocakmali.brewway.datamodel.GrinderView

class GrindersListAdapter(onClickCallback:((GrinderView?) -> Unit)? = null)
    : BaseSearchEquipmentAdapter<GrinderView>(DIFF_CALLBACK, onClickCallback)  {

    override fun itemName(item: GrinderView?) = item?.name

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GrinderView>() {
            override fun areItemsTheSame(oldItem: GrinderView, newItem: GrinderView): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GrinderView, newItem: GrinderView): Boolean {
                return oldItem == newItem
            }
        }
    }
}