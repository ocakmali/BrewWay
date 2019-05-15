package com.ocakmali.brewway.addeditrecipe.coffee

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ocakmali.brewway.addeditrecipe.base.AbstractSearchEquipmentViewModel
import com.ocakmali.brewway.datamodel.CoffeeView
import com.ocakmali.brewway.datamodel.toView
import com.ocakmali.common.DispatchersProvider
import com.ocakmali.domain.interactor.CoffeeInterActor
import com.ocakmali.domain.model.Coffee

class SearchCoffeeViewModel(private val interActor: CoffeeInterActor,
                            dispatchers: DispatchersProvider)
    : AbstractSearchEquipmentViewModel<Coffee>(dispatchers) {

    val coffees: LiveData<PagedList<CoffeeView>> =
            Transformations.switchMap(searchResult) { dataSource ->
                dataSource.map { coffee ->
                    coffee.toView()
                }.toLiveData(15)
            }

    override fun search(query: String) = interActor.searchCoffees(query)

    override fun loadEverything() = interActor.loadCoffees()
}