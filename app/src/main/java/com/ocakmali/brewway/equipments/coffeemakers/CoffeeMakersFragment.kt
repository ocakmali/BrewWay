package com.ocakmali.brewway.equipments.coffeemakers

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSnapHelper
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BaseFragment
import com.ocakmali.brewway.datamodel.CoffeeMakerView
import com.ocakmali.brewway.exceptions.EmptyItem
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener
import com.ocakmali.brewway.extensions.toast
import kotlinx.android.synthetic.main.fragment_coffee_makers.*
import kotlinx.android.synthetic.main.layout_equipments_grid_recycler.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoffeeMakersFragment : BaseFragment() {

    private val viewModel: CoffeeMakersViewModel by viewModel()

    override fun layoutResId(): Int = R.layout.fragment_coffee_makers

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = CoffeeMakersAdapter(object : CoffeeMakersActionListener {
            override fun onDoneClick(coffeeMakerView: CoffeeMakerView) {
                viewModel.addCoffeeMaker(coffeeMakerView)
            }

            override fun onDeleteClick(coffeeMakerView: CoffeeMakerView) {
                viewModel.deleteCoffeeMaker(coffeeMakerView)
            }
        })

        recycler_equipments.adapter = adapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler_equipments)

        with (viewModel) {
            coffeeMakers.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
            coffeeMakerInserted.observe(viewLifecycleOwner, Observer { coffeeMakerAddedSuccessfully() })
            failure.observe(viewLifecycleOwner, Observer(::notifyException))

            edit_coffee_maker.setOnActionDoneClickListener {
                addCoffeeMaker(CoffeeMakerView(it.text.toString()))
            }
        }
    }

    private fun coffeeMakerAddedSuccessfully() {
        if (!edit_coffee_maker.text.isNullOrEmpty()) {
            edit_coffee_maker.setText("")
        }
    }

    private fun notifyException(exception: Exception) {
        when (exception) {
            is EmptyItem -> toast(R.string.empty_coffee_maker_exception)
            else -> toast(R.string.something_went_wrong)
        }
    }
}