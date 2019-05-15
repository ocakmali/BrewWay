package com.ocakmali.brewway.equipments.coffees

import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.crashlytics.android.Crashlytics
import com.ocakmali.brewway.base.BaseViewModel
import com.ocakmali.brewway.datamodel.CoffeeView
import com.ocakmali.brewway.datamodel.toCoffee
import com.ocakmali.brewway.datamodel.toView
import com.ocakmali.brewway.exceptions.EmptyItem
import com.ocakmali.common.DispatchersProvider
import com.ocakmali.common.Result
import com.ocakmali.domain.interactor.CoffeeInterActor
import kotlinx.coroutines.launch

class CoffeesViewModel(private val interActor: CoffeeInterActor,
                       dispatchers: DispatchersProvider) : BaseViewModel(dispatchers) {

    val coffees = interActor.loadCoffees()
            .map { it.toView() }
            .toLiveData(20)
    val coffeeInserted = MutableLiveData<Unit>()

    fun addCoffee(coffeeView: CoffeeView) = launch {
        if (coffeeView.name.isEmpty()) {
            postFailure(EmptyItem)
        } else {
            interActor.addCoffee(coffeeView.toCoffee()).also {
                it.result(::coffeeInsertionFailed, ::coffeeInsertionSucceed)
            }
        }
    }

    private fun coffeeInsertionFailed(e: Exception) {
        Crashlytics.logException(e)
        postFailure(e)
    }

    private fun coffeeInsertionSucceed(u: Unit) {
        coffeeInserted.value = u
    }

    fun deleteCoffee(coffeeView: CoffeeView) = launch {
        interActor.deleteCoffee(coffeeView.toCoffee()).also {result ->
            if (result is Result.Error) {
                Crashlytics.logException(result.error)
                postFailure(result.error)
            }
        }
    }
}