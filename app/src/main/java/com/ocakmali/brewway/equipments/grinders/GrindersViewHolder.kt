package com.ocakmali.brewway.equipments.grinders

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BaseViewHolder
import com.ocakmali.brewway.datamodel.GrinderView
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener

class GrindersViewHolder(private val listener: GrindersActionListener,
                         view: View)
    : BaseViewHolder<GrinderView>(view) {

    private val grinderEditText: EditText = view.findViewById(R.id.edit_equipment)
    private val deleteButton: ImageButton = view.findViewById(R.id.btn_delete)

    override fun bind(obj: GrinderView) {
        grinderEditText.setText(obj.name)

        grinderEditText.setOnActionDoneClickListener {
            listener.onDoneClick(obj.copy(name = it.text.toString()))
            it.clearFocus()
        }

        deleteButton.setOnClickListener { listener.onDeleteClick(obj) }
    }
}
