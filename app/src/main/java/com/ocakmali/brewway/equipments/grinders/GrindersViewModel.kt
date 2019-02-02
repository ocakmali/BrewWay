package com.ocakmali.brewway.equipments.grinders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.ocakmali.brewway.base.BaseViewModel
import com.ocakmali.brewway.datamodel.GrinderView
import com.ocakmali.brewway.datamodel.toGrinder
import com.ocakmali.brewway.datamodel.toView
import com.ocakmali.brewway.exceptions.EmptyItem
import com.ocakmali.common.DispatchersProvider
import com.ocakmali.common.Result
import com.ocakmali.domain.interactor.GrinderInterActor
import kotlinx.coroutines.launch

class GrindersViewModel(private val interActor: GrinderInterActor,
                        dispatchers: DispatchersProvider) : BaseViewModel(dispatchers) {

    private val _grinderInsertion = MutableLiveData<Result<Exception, Unit>>()
    val grinders = interActor.loadGrinders()
            .map { it.toView() }
            .toLiveData(20)
    val grinderInsertion: LiveData<Result<Exception, Unit>>  get() = _grinderInsertion

    fun addGrinder(grinderView: GrinderView) = launch {
        if (grinderView.name.isEmpty()) {
            _grinderInsertion.value = Result.buildError(EmptyItem)
        } else {
            interActor.addGrinder(grinderView.toGrinder()).also {
                _grinderInsertion.value = it
            }
        }
    }

    fun deleteGrinder(grinderView: GrinderView) = launch {
        interActor.deleteGrinder(grinderView.toGrinder())
    }
}