package com.ocakmali.brewway.equipments.coffeemakers

import com.ocakmali.brewway.datamodel.CoffeeMakerView

interface CoffeeMakersActionListener {

    fun onDoneClick(coffeeMakerView: CoffeeMakerView)

    fun onDeleteClick(coffeeMakerView: CoffeeMakerView)
}
