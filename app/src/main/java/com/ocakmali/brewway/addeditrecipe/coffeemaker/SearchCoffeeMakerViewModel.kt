package com.ocakmali.brewway.addeditrecipe.coffeemaker

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ocakmali.brewway.addeditrecipe.base.AbstractSearchEquipmentViewModel
import com.ocakmali.brewway.datamodel.CoffeeMakerView
import com.ocakmali.brewway.datamodel.toView
import com.ocakmali.domain.interactor.CoffeeMakerInterActor
import com.ocakmali.domain.model.CoffeeMaker

class SearchCoffeeMakerViewModel(private val interActor: CoffeeMakerInterActor)
    : AbstractSearchEquipmentViewModel<CoffeeMaker>() {

    val coffeeMakers: LiveData<PagedList<CoffeeMakerView>> =
            Transformations.switchMap(searchResult) { dataSource ->
                dataSource.map { coffeeMaker->
                    coffeeMaker.toView() }.toLiveData(15) }

    override fun search(query: String) = interActor.searchCoffeeMakers(query)

    override fun loadEverything() = interActor.loadCoffeeMakers()
}
