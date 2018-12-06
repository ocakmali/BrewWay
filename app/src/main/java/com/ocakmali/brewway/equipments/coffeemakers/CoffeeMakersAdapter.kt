package com.ocakmali.brewway.equipments.coffeemakers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BasePagedListAdapter
import com.ocakmali.brewway.datamodel.CoffeeMakerView

class CoffeeMakersAdapter(private val listener: CoffeeMakersActionListener)
    : BasePagedListAdapter<CoffeeMakerView>(DIFF_UTIL){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeMakersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_equipments, parent, false)
        return CoffeeMakersViewHolder(listener, view)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<CoffeeMakerView>() {
            override fun areItemsTheSame(oldItem: CoffeeMakerView, newItem: CoffeeMakerView) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CoffeeMakerView, newItem: CoffeeMakerView) = oldItem == newItem
        }
    }
}