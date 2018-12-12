package com.ocakmali.brewway.equipments.coffeemakers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.example.common.DispatchersProvider
import com.ocakmali.brewway.base.BaseViewModel
import com.ocakmali.brewway.datamodel.CoffeeMakerView
import com.ocakmali.brewway.datamodel.toCoffeeMaker
import com.ocakmali.brewway.datamodel.toView
import com.ocakmali.brewway.exceptions.EmptyItem
import com.ocakmali.domain.interactor.CoffeeMakerInterActor
import com.ocakmali.domain.model.Result
import kotlinx.coroutines.launch

class CoffeeMakersViewModel(private val interActor: CoffeeMakerInterActor,
                            dispatcher: DispatchersProvider) : BaseViewModel(dispatcher) {

    private val _coffeeMakerInsertion = MutableLiveData<Result<Exception, Unit>>()
    private val _coffeeMakerDeletion = MutableLiveData<Result<Exception, Unit>>()

    val coffeeMakers = interActor.loadCoffeeMakers()
            .map { it.toView() }
            .toLiveData(20)

    val coffeeMakerInsertion: LiveData<Result<Exception, Unit>>
        get() = _coffeeMakerInsertion

    val coffeeMakerDeletion: LiveData<Result<Exception, Unit>>
        get() = _coffeeMakerDeletion

    fun addCoffeeMaker(coffeeMakerView: CoffeeMakerView) = launch {
        if (coffeeMakerView.name.isEmpty()) {
            _coffeeMakerInsertion.value = Result.buildError(EmptyItem)
        } else {
            interActor.addCoffeeMaker(coffeeMakerView.toCoffeeMaker()).also {
                _coffeeMakerInsertion.value = it
            }
        }
    }

    fun deleteCoffeeMaker(coffeeMakerView: CoffeeMakerView) = launch {
        interActor.deleteCoffeeMaker(coffeeMakerView.toCoffeeMaker()).also {
            _coffeeMakerDeletion.value = it
        }
    }
}