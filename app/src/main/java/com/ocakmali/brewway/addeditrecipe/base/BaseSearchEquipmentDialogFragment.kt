package com.ocakmali.brewway.addeditrecipe.base

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ocakmali.brewway.R
import com.ocakmali.brewway.addeditrecipe.AddEditRecipeViewModel
import com.ocakmali.brewway.base.BaseFullScreenWithAnimationDialogFragment
import com.ocakmali.brewway.extensions.addLifecycleTextWatcher
import kotlinx.android.synthetic.main.fragment_search_equipment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class BaseSearchEquipmentDialogFragment : BaseFullScreenWithAnimationDialogFragment() {

    override fun layoutResId() = R.layout.fragment_search_equipment

    protected abstract val searchViewModel: AbstractSearchEquipmentViewModel<*>
    protected val addEditRecipeViewModel by sharedViewModel<AddEditRecipeViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateEditText(startSearchItem())

        et_search_equipment.setHint(hintResId())
        rcv_items.layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL,
                false)
        rcv_items.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        et_search_equipment.addLifecycleTextWatcher(lifecycle) {
            searchViewModel.searchItem(it.toString())
        }

        back.setOnClickListener {
            dismiss()
        }
    }

    abstract fun startSearchItem(): String?

    abstract fun hintResId(): Int

    protected fun updateEditText(text: String?) {
        et_search_equipment.setText(text, TextView.BufferType.EDITABLE)
        searchViewModel.searchItem(text ?: "")
    }
}