package com.ocakmali.domain.repository

import com.ocakmali.common.Result

interface Searchable<T> {

    suspend fun search(query: String, limit: Int): Result<Exception, List<T>>
}
