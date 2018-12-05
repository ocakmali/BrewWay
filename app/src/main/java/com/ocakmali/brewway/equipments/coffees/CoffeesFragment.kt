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
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener
import kotlinx.android.synthetic.main.fragment_coffees.*
import kotlinx.android.synthetic.main.layout_equipments_grid_recycler.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoffeesFragment : Fragment() {

    private val viewModel: CoffeeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coffees, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                it.result({}, {
                    edit_coffee.setText("")
                    edit_coffee.clearFocus()
                })
            })

            edit_coffee.setOnActionDoneClickListener {
                addCoffee(CoffeeView(it.text.toString()))
            }
        }
    }
}