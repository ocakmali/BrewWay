package com.ocakmali.brewway.addeditrecipe.coffeemaker

import android.os.Bundle
import androidx.lifecycle.Observer
import com.ocakmali.brewway.R
import com.ocakmali.brewway.addeditrecipe.base.BaseSearchEquipmentDialogFragment
import kotlinx.android.synthetic.main.fragment_search_equipment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchCoffeeMakerDialogFragment : BaseSearchEquipmentDialogFragment() {

    override val searchViewModel by viewModel<SearchCoffeeMakerViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = CoffeeMakersListAdapter {
            addEditRecipeViewModel.updateCoffeeMaker(it)
            dismiss()
        }
        rcv_items.adapter = adapter

        searchViewModel.coffeeMakers.observe(viewLifecycleOwner, Observer { pagedList ->
            adapter.submitList(pagedList)
        })
    }

    override fun startSearchItem(): String?  = addEditRecipeViewModel.recipe.value?.equipment?.coffeeMaker?.name

    override fun hintResId() = R.string.hint_search_coffee_maker
}