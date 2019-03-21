package com.ocakmali.data.extensions

fun String.startsWith(): String {
    return String.format("%s*", this)
}

fun String.exactWord(): String {
    return String.format("\"%s\"", this)
}