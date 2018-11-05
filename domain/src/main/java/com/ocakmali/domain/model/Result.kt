package com.ocakmali.domain.model

sealed class Result<out E, out V> {

    data class Value<out V>(val value: V) : Result<Nothing, V>()
    data class Error<out E>(val error: E) : Result<E, Nothing>()

    companion object {
        fun <V> buildValue(value: V) = Value(value)

        fun <E> buildError(error: E) = Error(error)
    }
}