package com.ocakmali.brewway.equipments.coffees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSnapHelper
import com.ocakmali.brewway.R
import com.ocakmali.brewway.datamodel.CoffeeView
import com.ocakmali.brewway.exceptions.EmptyItem
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener
import com.ocakmali.brewway.extensions.toast
import kotlinx.android.synthetic.main.fragment_coffees.*
import kotlinx.android.synthetic.main.layout_equipments_grid_recycler.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoffeesFragment : Fragment() {

    private val viewModel: CoffeesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coffees, container, false)
    }

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
            coffees.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })

            coffeeInsertion.observe(viewLifecycleOwner, Observer {
                it.result(::notifyException, ::coffeeAddedSuccessfully)
            })

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