package com.xinh.domain.validation.rule

import com.xinh.domain.validation.ValidationRule
import com.xinh.domain.validation.error.LengthPhoneNumberError
import com.xinh.domain.validation.error.ValidationError

class LengthPhoneNumberRule : ValidationRule<String> {
    companion object{
        private const val MIN_LENGTH = 8
        private const val MAX_LENGTH = 11
    }

    override fun validate(value: String?): Boolean {
        return value?.length in MIN_LENGTH..MAX_LENGTH
    }

    override fun getError(): ValidationError {
        return LengthPhoneNumberError()
    }
}