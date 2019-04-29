package com.ocakmali.domain.repository

import androidx.paging.DataSource

interface Searchable<T> {

    fun search(query: String): DataSource.Factory<Int, T>
}
