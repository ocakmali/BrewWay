package com.ocakmali.brewway.datamodel

import com.ocakmali.domain.model.CoffeeMaker

internal fun CoffeeMakerView.toCoffeeMaker() = CoffeeMaker(name, id)

internal fun CoffeeMaker.toView() = CoffeeMakerView(name, id)

data class CoffeeMakerView(val name: String,
                           val id: Int = 0)