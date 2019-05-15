package com.ocakmali.brewway.addeditrecipe.grinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ocakmali.brewway.addeditrecipe.base.AbstractSearchEquipmentViewModel
import com.ocakmali.brewway.datamodel.GrinderView
import com.ocakmali.brewway.datamodel.toView
import com.ocakmali.common.DispatchersProvider
import com.ocakmali.domain.interactor.GrinderInterActor
import com.ocakmali.domain.model.Grinder

class SearchGrinderViewModel(private val interActor: GrinderInterActor,
                             dispatchersProvider: DispatchersProvider)
    : AbstractSearchEquipmentViewModel<Grinder>(dispatchersProvider) {

    val grinders: LiveData<PagedList<GrinderView>> =
            Transformations.switchMap(searchResult) { dataSource ->
                dataSource.map { grinder ->
                    grinder.toView()
                }.toLiveData(15)
            }

    override fun search(query: String) = interActor.searchGrinders(query)

    override fun loadEverything() = interActor.loadGrinders()
}