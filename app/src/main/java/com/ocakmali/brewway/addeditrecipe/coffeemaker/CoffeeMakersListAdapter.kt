package com.ocakmali.brewway.addeditrecipe.coffeemaker

import androidx.recyclerview.widget.DiffUtil
import com.ocakmali.brewway.addeditrecipe.base.BaseSearchEquipmentAdapter
import com.ocakmali.brewway.datamodel.CoffeeMakerView

class CoffeeMakersListAdapter(onClickCallback: ((CoffeeMakerView?) -> Unit)? = null)
    : BaseSearchEquipmentAdapter<CoffeeMakerView>(DIFF_CALLBACK, onClickCallback) {

    override fun itemName(item: CoffeeMakerView?) = item?.name

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CoffeeMakerView>() {
            override fun areItemsTheSame(oldItem: CoffeeMakerView, newItem: CoffeeMakerView): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CoffeeMakerView, newItem: CoffeeMakerView): Boolean {
                return oldItem == newItem
            }
        }
    }
}