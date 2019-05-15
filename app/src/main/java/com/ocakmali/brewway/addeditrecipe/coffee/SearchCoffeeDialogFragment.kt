package com.ocakmali.brewway.addeditrecipe.coffee

import android.os.Bundle
import androidx.lifecycle.Observer
import com.ocakmali.brewway.R
import com.ocakmali.brewway.addeditrecipe.base.BaseSearchEquipmentDialogFragment
import kotlinx.android.synthetic.main.fragment_search_equipment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchCoffeeDialogFragment : BaseSearchEquipmentDialogFragment(){

    override val searchViewModel by viewModel<SearchCoffeeViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = CoffeesListAdapter {
            addEditRecipeViewModel.updateCoffee(it)
            dismiss()
        }
        rcv_items.adapter = adapter

        searchViewModel.coffees.observe(viewLifecycleOwner, Observer { pagedList ->
            adapter.submitList(pagedList)
        })
    }

    override fun startSearchItem(): String?  = addEditRecipeViewModel.recipe.value?.equipment?.coffee?.name

    override fun hintResId() = R.string.hint_search_coffee
}