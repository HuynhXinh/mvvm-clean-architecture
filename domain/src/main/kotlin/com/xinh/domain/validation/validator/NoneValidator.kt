package com.xinh.domain.validation.validator

import com.xinh.domain.validation.Validator
import com.xinh.domain.validation.ValueProvider

/**
 * Create by Mr.X on 1/25/19
 */
class NoneValidator : Validator<String>(object : ValueProvider<String> {
    override fun getValue(): String? {
        return null
    }
}) {

    override fun addRules() {}

    override fun isValid(): Boolean {
        return true
    }
}