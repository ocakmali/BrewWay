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

import com.ocakmali.domain.model.CoffeeMaker
import com.ocakmali.domain.model.Result
import com.ocakmali.domain.repository.ICoffeeMakerRepository

class CoffeeMakerInteractor(private val repository: ICoffeeMakerRepository) {

    suspend fun loadCoffeeMakers(handleResult: Result<Exception, List<CoffeeMaker>>.() -> Unit) {
        handleResult(repository.loadCoffeeMakers())
    }

    suspend fun addCoffeeMaker(coffeeMaker: CoffeeMaker, handleResult: Result<Exception, Unit>.() -> Unit) {
        handleResult(repository.addCoffeeMaker(coffeeMaker))
    }

    suspend fun addCoffeeMakers(coffeeMakers: List<CoffeeMaker>, handleResult: Result<Exception, Unit>.() -> Unit) {
        handleResult(repository.addCoffeeMakers(coffeeMakers))
    }

    suspend fun deleteCoffeeMaker(coffeeMaker: CoffeeMaker, handleResult: Result<Exception, Unit>.() -> Unit) {
        handleResult(repository.deleteCoffeeMaker(coffeeMaker))
    }

    companion object {
        private var INSTANCE: CoffeeMakerInteractor? = null

        fun getInstance(repository: ICoffeeMakerRepository): CoffeeMakerInteractor {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CoffeeMakerInteractor(repository).also { INSTANCE = it }
            }
        }
    }
}