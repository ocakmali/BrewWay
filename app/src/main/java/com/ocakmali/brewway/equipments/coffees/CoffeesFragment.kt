package com.ocakmali.brewway.equipments.coffees

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSnapHelper
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BaseFragment
import com.ocakmali.brewway.datamodel.CoffeeView
import com.ocakmali.brewway.exceptions.EmptyItem
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener
import com.ocakmali.brewway.extensions.toast
import kotlinx.android.synthetic.main.fragment_coffees.*
import kotlinx.android.synthetic.main.layout_equipments_grid_recycler.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoffeesFragment : BaseFragment() {

    private val viewModel by viewModel<CoffeesViewModel>()

    override fun layoutResId(): Int = R.layout.fragment_coffees

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = CoffeesAdapter(object : CoffeesActionListener {
            override fun onDoneClick(coffeeView: CoffeeView) {
                viewModel.addCoffee(coffeeView)
            }

            override fun onDeleteClick(coffeeView: CoffeeView) {
                viewModel.deleteCoffee(coffeeView)
            }
        })

        recycler_equipments.adapter = adapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler_equipments)

        with(viewModel) {
            coffees.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
            coffeeInserted.observe(viewLifecycleOwner, Observer { coffeeAddedSuccessfully() })
            failure.observe(viewLifecycleOwner, Observer(::notifyException))

            edit_coffee.setOnActionDoneClickListener {
                addCoffee(CoffeeView(it.text.toString()))
            }
        }
    }

    private fun coffeeAddedSuccessfully() {
        if (!edit_coffee.text.isNullOrEmpty()) {
            edit_coffee.setText("")
        }
    }

    private fun notifyException(exception: Exception) {
        when (exception) {
            is EmptyItem -> toast(R.string.empty_coffee_exception)
            else -> toast(R.string.something_went_wrong)
        }
    }
}