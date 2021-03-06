package com.ocakmali.brewway.equipments.grinders

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSnapHelper
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BaseFragment
import com.ocakmali.brewway.datamodel.GrinderView
import com.ocakmali.brewway.exceptions.EmptyItem
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener
import com.ocakmali.brewway.extensions.toast
import kotlinx.android.synthetic.main.fragment_grinders.*
import kotlinx.android.synthetic.main.layout_equipments_grid_recycler.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GrindersFragment : BaseFragment() {

    private val viewModel: GrindersViewModel by viewModel()

    override fun layoutResId(): Int = R.layout.fragment_grinders

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = GrindersAdapter(object : GrindersActionListener {
            override fun onDoneClick(grinder: GrinderView) {
                viewModel.addGrinder(grinder)
            }

            override fun onDeleteClick(grinder: GrinderView) {
                viewModel.deleteGrinder(grinder)
            }
        })

        recycler_equipments.adapter = adapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler_equipments)

        with(viewModel) {
            grinders.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
            grinderInserted.observe(viewLifecycleOwner, Observer { grinderAddedSuccessfully() })
            failure.observe(viewLifecycleOwner, Observer(::notifyException))

            edit_grinder.setOnActionDoneClickListener {
                addGrinder(GrinderView(it.text.toString()))
            }
        }
    }

    private fun grinderAddedSuccessfully() {
        if (!edit_grinder.text.isNullOrEmpty()) {
            edit_grinder.setText("")
        }
    }

    private fun notifyException(exception: Exception) {
        when (exception) {
            is EmptyItem -> toast(R.string.empty_grinder_exception)
            else -> toast(R.string.something_went_wrong)
        }
    }
}