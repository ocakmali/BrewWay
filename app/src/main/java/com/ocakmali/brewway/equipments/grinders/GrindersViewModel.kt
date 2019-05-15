package com.ocakmali.brewway.equipments.grinders

import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.crashlytics.android.Crashlytics
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

    val grinders = interActor.loadGrinders()
            .map { it.toView() }
            .toLiveData(20)
    val grinderInserted = MutableLiveData<Unit>()

    fun addGrinder(grinderView: GrinderView) = launch {
        if (grinderView.name.isEmpty()) {
           postFailure(EmptyItem)
        } else {
            interActor.addGrinder(grinderView.toGrinder()).also {
                it.result(::grinderInsertionFailed, ::grinderInsertionSucceed)
            }
        }
    }

    private fun grinderInsertionFailed(e: Exception) {
        Crashlytics.logException(e)
        postFailure(e)
    }

    private fun grinderInsertionSucceed(u: Unit) {
        grinderInserted.value = u
    }

    fun deleteGrinder(grinderView: GrinderView) = launch {
        interActor.deleteGrinder(grinderView.toGrinder()).also {result ->
            if (result is Result.Error) {
                Crashlytics.logException(result.error)
                postFailure(result.error)
            }
        }
    }
}