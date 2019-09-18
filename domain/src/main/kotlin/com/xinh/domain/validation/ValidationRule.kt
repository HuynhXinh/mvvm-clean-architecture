package com.xinh.domain.validation

import com.xinh.domain.validation.error.ValidationError

/**
 * Create by Mr.X on 1/25/19
 */
interface ValidationRule<ValueType> {
    fun validate(value: ValueType?): Boolean

    fun getError(): ValidationError
}