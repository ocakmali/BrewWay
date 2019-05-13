package com.ocakmali.brewway.addeditrecipe.grinder

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ocakmali.brewway.R
import com.ocakmali.brewway.addeditrecipe.base.BaseSearchEquipmentDialogFragment
import kotlinx.android.synthetic.main.fragment_search_equipment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchGrinderDialogFragment : BaseSearchEquipmentDialogFragment() {

    override val searchViewModel by viewModel<SearchGrinderViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = GrindersListAdapter {
            addEditRecipeViewModel.updateGrinder(it)
            dismiss()
        }
        rcv_items.adapter = adapter

        searchViewModel.grinders.observe(viewLifecycleOwner, Observer { pagedList ->
            adapter.submitList(pagedList)
        })
    }

    override fun startSearchItem() = addEditRecipeViewModel.recipe.value?.equipment?.grinder?.name

    override fun hintResId() = R.string.hint_search_grinder
}