/*
 * MIT License
 *
 * Copyright (c) 2018 Mehmet Ali Ocak
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

package com.example.data.repository

import com.example.data.dao.CoffeeMakerDao
import com.example.data.entity.toCoffeeMaker
import com.example.data.entity.toEntity
import com.ocakmali.common.DispatchersProvider
import com.ocakmali.domain.model.CoffeeMaker
import com.ocakmali.domain.model.Result
import com.ocakmali.domain.repository.ICoffeeMakerRepository
import kotlinx.coroutines.withContext

class CoffeeMakerRepository(
        private val dao: CoffeeMakerDao,
        private val dispatchers: DispatchersProvider
) : ICoffeeMakerRepository {

    override fun loadCoffeeMakers() = dao.loadCoffeeMakers().map { it.toCoffeeMaker() }

    override suspend fun addCoffeeMaker(coffeeMaker: CoffeeMaker): Result<Exception, Unit> {
        return Result.buildValue {
            withContext(dispatchers.io) { dao.insert(coffeeMaker.toEntity()) }
        }
    }

    override suspend fun addCoffeeMakers(coffeeMakers: List<CoffeeMaker>): Result<Exception, Unit> {
        return Result.buildValue {
            withContext(dispatchers.io) {
                dao.insert(coffeeMakers.map { maker ->
                    maker.toEntity()
                })
            }
        }
    }

    override suspend fun deleteCoffeeMaker(coffeeMaker: CoffeeMaker): Result<Exception, Unit> {
        return Result.buildValue {
            withContext(dispatchers.io) { dao.delete(coffeeMaker.toEntity()) }
        }
    }
}