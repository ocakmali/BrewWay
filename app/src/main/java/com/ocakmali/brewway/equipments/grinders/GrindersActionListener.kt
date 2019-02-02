package com.ocakmali.brewway.equipments.grinders

import com.ocakmali.brewway.datamodel.GrinderView

interface GrindersActionListener {

    fun onDoneClick(grinder: GrinderView)

    fun onDeleteClick(grinder: GrinderView)
}