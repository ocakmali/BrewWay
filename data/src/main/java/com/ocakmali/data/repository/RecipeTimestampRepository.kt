/*
 * MIT License
 *
 * Copyright (c) 2019 Mehmet Ali Ocak
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ocakmali.data.repository

import com.ocakmali.common.DispatchersProvider
import com.ocakmali.common.Result
import com.ocakmali.data.dao.RecipeTimestampDao
import com.ocakmali.data.entity.toEntity
import com.ocakmali.data.entity.toRecipeTimestamp
import com.ocakmali.domain.model.RecipeTimestamp
import com.ocakmali.domain.repository.IRecipeTimestampRepository
import kotlinx.coroutines.withContext

class RecipeTimestampRepository(private val dao: RecipeTimestampDao,
                                private val dispatchers: DispatchersProvider)
    : IRecipeTimestampRepository {

    override suspend fun loadTimestampsByRecipeId(recipeId: Int) = withContext(dispatchers.io) {
        Result.value { dao.loadTimestampsByRecipeId(recipeId).map { it.toRecipeTimestamp() } }
    }

    override suspend fun addTimestamp(timestamp: RecipeTimestamp) = withContext(dispatchers.io) {
        Result.value { dao.insert(timestamp.toEntity()) }
    }

    override suspend fun addTimestamps(timestamps: List<RecipeTimestamp>) = withContext(dispatchers.io) {
        Result.value { dao.insert(timestamps.map { it.toEntity() }) }
    }

    override suspend fun deleteTimestamp(timestamp: RecipeTimestamp) = withContext(dispatchers.io) {
        Result.value { dao.delete(timestamp.toEntity()) }
    }
}