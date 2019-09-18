package com.xinh.domain.validation.rule

import com.xinh.domain.validation.ValidationRule
import com.xinh.domain.extension.isNotNullOrBlank
import com.xinh.domain.validation.error.RequiredFieldError
import com.xinh.domain.validation.error.ValidationError

/**
 * Create by Mr.X on 1/25/19
 */
class RequiredFieldRule : ValidationRule<String> {
    override fun validate(value: String?): Boolean {
        return value.isNotNullOrBlank()
    }

    override fun getError(): ValidationError {
        return RequiredFieldError()
    }
}
