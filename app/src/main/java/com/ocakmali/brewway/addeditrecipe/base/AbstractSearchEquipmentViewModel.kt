package com.ocakmali.brewway.addeditrecipe.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ocakmali.brewway.base.BaseViewModel
import com.ocakmali.common.DispatchersProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import java.util.*

abstract class AbstractSearchEquipmentViewModel<T>(dispatchers: DispatchersProvider)
    : BaseViewModel(dispatchers) {

    private lateinit var query: String
    protected val searchResult = MutableLiveData<DataSource.Factory<Int, T>>()
    private val actor: SendChannel<String> = actor (capacity = Channel.CONFLATED) {
        consumeEach { query ->
            if (query.isBlank()) {
                searchResult.value = loadEverything()
            } else {
                searchResult.value = search(query)
            }
            delay(250L)
        }
    }

    fun searchItem(query: String) {
        if (shouldSearch(query)) {
            offerActor(this.query)
        }
    }

    private fun shouldSearch(newQuery: String): Boolean {
        val formattedQuery = newQuery.toLowerCase(Locale.US).trim()

        if (::query.isInitialized.not() || query != formattedQuery) {
            query = formattedQuery
            return true
        }
        return false
    }

    private fun offerActor(query: String) {
        if (actor.isClosedForSend.not()) {
            actor.offer(query)
        }
    }

    protected abstract fun search(query: String): DataSource.Factory<Int, T>

    protected abstract fun loadEverything(): DataSource.Factory<Int, T>
}