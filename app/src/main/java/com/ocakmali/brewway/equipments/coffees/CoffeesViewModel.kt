package com.ocakmali.brewway.equipments.coffees

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.ocakmali.brewway.base.BaseViewModel
import com.ocakmali.brewway.datamodel.CoffeeView
import com.ocakmali.brewway.datamodel.toCoffee
import com.ocakmali.brewway.datamodel.toView
import com.ocakmali.brewway.exceptions.EmptyItem
import com.ocakmali.common.DispatchersProvider
import com.ocakmali.domain.interactor.CoffeeInterActor
import com.ocakmali.domain.model.Result
import kotlinx.coroutines.launch

class CoffeesViewModel(private val interActor: CoffeeInterActor,
                       dispatchers: DispatchersProvider) : BaseViewModel(dispatchers) {

    private val _coffeeInsertion = MutableLiveData<Result<Exception, Unit>>()

    val coffees = interActor.loadCoffees()
            .map { it.toView() }
            .toLiveData(20)
    val coffeeInsertion: LiveData<Result<Exception,Unit>> = _coffeeInsertion

    fun addCoffee(coffeeView: CoffeeView) = launch {
        if (coffeeView.name.isEmpty()) {
            _coffeeInsertion.value = Result.buildError(EmptyItem)
        } else {
            interActor.addCoffee(coffeeView.toCoffee()).also {
                _coffeeInsertion.value = it
            }
        }
    }

    fun deleteCoffee(coffeeView: CoffeeView) = launch {
        interActor.deleteCoffee(coffeeView.toCoffee())
    }
}