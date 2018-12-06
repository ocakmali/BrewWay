package com.ocakmali.brewway.equipments.coffeemakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSnapHelper
import com.ocakmali.brewway.R
import com.ocakmali.brewway.datamodel.CoffeeMakerView
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener
import kotlinx.android.synthetic.main.fragment_coffee_makers.*
import kotlinx.android.synthetic.main.layout_equipments_grid_recycler.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoffeeMakersFragment : Fragment() {

    private val viewModel: CoffeeMakersViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coffee_makers, container, false)
    }

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
            coffeeMakers.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })

            coffeeMakerInsertion.observe(viewLifecycleOwner, Observer {
                it.result({},{
                    if (!edit_coffee_maker.text.isNullOrEmpty()) {
                        edit_coffee_maker.setText("")
                    }
                })
            })

            edit_coffee_maker.setOnActionDoneClickListener {
                addCoffeeMaker(CoffeeMakerView(it.text.toString()))
            }
        }
    }
}