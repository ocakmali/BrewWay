package com.ocakmali.brewway.equipments.grinders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSnapHelper
import com.ocakmali.brewway.R
import com.ocakmali.brewway.datamodel.GrinderView
import com.ocakmali.brewway.exceptions.EmptyItem
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener
import com.ocakmali.brewway.extensions.toast
import kotlinx.android.synthetic.main.fragment_grinders.*
import kotlinx.android.synthetic.main.layout_equipments_grid_recycler.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GrindersFragment : Fragment() {

    private val viewModel: GrindersViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grinders, container, false)
    }

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
            grinders.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })

            grinderInsertion.observe(viewLifecycleOwner, Observer {
                it.result(::notifyException, ::grinderAddedSuccessfully)
            })

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