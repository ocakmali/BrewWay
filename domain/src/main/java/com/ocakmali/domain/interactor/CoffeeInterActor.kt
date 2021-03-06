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

package com.ocakmali.domain.interactor

import com.ocakmali.domain.model.Coffee
import com.ocakmali.domain.repository.ICoffeeRepository

class CoffeeInterActor(private val repository: ICoffeeRepository) {

    fun loadCoffees() = repository.loadCoffees()

    fun searchCoffees(query: String) = repository.search(query)

    suspend fun addCoffee(coffee: Coffee) = repository.addCoffee(coffee)

    suspend fun addCoffees(coffees: List<Coffee>) = repository.addCoffees(coffees)

    suspend fun deleteCoffee(coffee: Coffee) = repository.deleteCoffee(coffee)

    suspend fun findByName(name: String) = repository.findByName(name)
}