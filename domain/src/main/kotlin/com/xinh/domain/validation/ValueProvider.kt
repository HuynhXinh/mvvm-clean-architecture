package com.xinh.domain.validation

/**
 * Create by Mr.X on 1/25/19
 */
interface ValueProvider<ValueType> {
    fun getValue(): ValueType?
}