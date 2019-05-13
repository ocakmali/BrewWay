package com.ocakmali.brewway.addeditrecipe.coffee

import androidx.recyclerview.widget.DiffUtil
import com.ocakmali.brewway.addeditrecipe.base.BaseSearchEquipmentAdapter
import com.ocakmali.brewway.datamodel.CoffeeView

class CoffeesListAdapter(onClickCallback: ((CoffeeView?) -> Unit)? = null)
    : BaseSearchEquipmentAdapter<CoffeeView>(DIFF_CALLBACK, onClickCallback) {

    override fun itemName(item: CoffeeView?) = item?.name

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CoffeeView>() {
            override fun areItemsTheSame(oldItem: CoffeeView, newItem: CoffeeView): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CoffeeView, newItem: CoffeeView): Boolean {
                return oldItem == newItem
            }
        }
    }
}