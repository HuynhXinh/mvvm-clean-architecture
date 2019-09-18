package com.xinh.domain.validation

import com.xinh.domain.validation.error.ValidationError

/**
 * Create by Mr.X on 1/27/19
 */
interface IValidator {

    fun validate(): List<ValidationResult>

    fun isValid(): Boolean

    fun getError(): ValidationError?

    fun getErrors(): List<ValidationError>
}
