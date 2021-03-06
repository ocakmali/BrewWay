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

package com.ocakmali.common

sealed class Result<out E, out V> {

    data class Value<out V>(val value: V) : Result<Nothing, V>()
    data class Error<out E>(val error: E) : Result<E, Nothing>()

    inline fun result(fnE: (E) -> Unit, fnV: (V) -> Unit) {
        when (this) {
            is Error -> fnE(error)
            is Value -> fnV(value)
        }
    }

    inline fun result(fnE: (E) -> Unit, fnV: () -> Unit) {
        when (this) {
            is Error -> fnE(error)
            is Value -> fnV()
        }
    }

    companion object {
        inline fun <V> value(action: () -> V): Result<Exception, V> {
            return try {
                Value(action())
            } catch (e: Exception){
                Error(e)
            }
        }

        fun <E> buildError(error: E) = Error(error)
    }
}

fun<E, V, N> Result<E,V>.map(fn: (V) -> N): Result<E, N> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Value -> Result.Value(fn(value))
    }
}