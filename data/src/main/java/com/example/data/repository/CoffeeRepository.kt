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

import com.example.common.DispatchersProvider
import com.example.data.dao.CoffeeDao
import com.example.data.entity.CoffeeEntity
import com.example.data.mapper.EntityMapper
import com.ocakmali.domain.model.Coffee
import com.ocakmali.domain.model.Result
import com.ocakmali.domain.repository.ICoffeeRepository
import kotlinx.coroutines.withContext

class CoffeeRepository(
        private val dao: CoffeeDao,
        private val mapper: EntityMapper<Coffee, CoffeeEntity>,
        private val dispatchers: DispatchersProvider
) : ICoffeeRepository {

    override suspend fun loadCoffees(): Result<Exception, List<Coffee>> {
       return Result.buildValue {
           withContext(dispatchers.io) { dao.loadCoffees().map { mapper.mapFromEntity(it) } }
       }
    }

    override suspend fun addCoffee(coffee: Coffee): Result<Exception, Unit> {
        return Result.buildValue {
            withContext(dispatchers.io) { dao.insert(mapper.mapToEntity(coffee)) }
        }
    }

    override suspend fun addCoffees(coffees: List<Coffee>): Result<Exception, Unit> {
        return Result.buildValue {
            withContext(dispatchers.io) { dao.insert(coffees.map { mapper.mapToEntity(it) }) }
        }
    }

    override suspend fun deleteCoffee(coffee: Coffee): Result<Exception, Unit> {
        return Result.buildValue {
            withContext(dispatchers.io) { dao.delete(mapper.mapToEntity(coffee)) }
        }
    }

    companion object {
        private var INSTANCE: CoffeeRepository? = null

        fun getInstance(coffeeDao: CoffeeDao,
                        mapper: EntityMapper<Coffee, CoffeeEntity>,
                        dispatchers: DispatchersProvider): CoffeeRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CoffeeRepository(coffeeDao, mapper, dispatchers).also { INSTANCE = it }
            }
        }
    }
}