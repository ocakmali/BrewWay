package com.ocakmali.brewway.equipments.coffeemakers

import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.crashlytics.android.Crashlytics
import com.ocakmali.brewway.base.BaseViewModel
import com.ocakmali.brewway.datamodel.CoffeeMakerView
import com.ocakmali.brewway.datamodel.toCoffeeMaker
import com.ocakmali.brewway.datamodel.toView
import com.ocakmali.brewway.exceptions.EmptyItem
import com.ocakmali.common.Result
import com.ocakmali.domain.interactor.CoffeeMakerInterActor
import kotlinx.coroutines.launch

class CoffeeMakersViewModel(private val interActor: CoffeeMakerInterActor) : BaseViewModel() {

    val coffeeMakers = interActor.loadCoffeeMakers()
            .map { it.toView() }
            .toLiveData(20)

    val coffeeMakerInserted = MutableLiveData<Unit>()

    fun addCoffeeMaker(coffeeMakerView: CoffeeMakerView) = launch {
        if (coffeeMakerView.name.isEmpty()) {
            postFailure(EmptyItem)
        } else {
            interActor.addCoffeeMaker(coffeeMakerView.toCoffeeMaker()).also {
                it.result(::coffeeMakerInsertionFailed, ::coffeeMakerInsertionSucceed)
            }
        }
    }

    private fun coffeeMakerInsertionFailed(e: Exception) {
        Crashlytics.logException(e)
        postFailure(e)
    }

    private fun coffeeMakerInsertionSucceed(u: Unit) {
        coffeeMakerInserted.value = u
    }

    fun deleteCoffeeMaker(coffeeMakerView: CoffeeMakerView) = launch {
        interActor.deleteCoffeeMaker(coffeeMakerView.toCoffeeMaker()).also { result ->
            if (result is Result.Error) {
                Crashlytics.logException(result.error)
                postFailure(result.error)
            }
        }
    }
}