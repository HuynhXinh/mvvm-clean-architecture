package com.xinh.domain.extension

const val EMPTY = ""

fun String?.safe(): String {
    return this ?: EMPTY
}

fun String?.isNotNullOrBlank(): Boolean = !this.isNullOrBlank()