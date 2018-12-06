package com.ocakmali.brewway.equipments.coffeemakers

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BaseViewHolder
import com.ocakmali.brewway.datamodel.CoffeeMakerView
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener

class CoffeeMakersViewHolder(private val listener: CoffeeMakersActionListener,
                             view: View
) : BaseViewHolder<CoffeeMakerView>(view) {

    private val coffeeMakerEditText: EditText = view.findViewById(R.id.edit_equipment)
    private val deleteButton: ImageButton = view.findViewById(R.id.btn_delete)

    override fun bind(obj: CoffeeMakerView) {

        coffeeMakerEditText.setText(obj.name)

        coffeeMakerEditText.setOnActionDoneClickListener {
            listener.onDoneClick(obj.copy(name = it.text.toString()))
            it.clearFocus()
        }

        deleteButton.setOnClickListener { listener.onDeleteClick(obj) }
    }
}