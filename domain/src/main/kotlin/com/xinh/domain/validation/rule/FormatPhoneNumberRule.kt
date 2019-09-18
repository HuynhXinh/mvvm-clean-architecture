package com.xinh.domain.validation.rule

import com.xinh.domain.extension.isNotNullOrBlank
import com.xinh.domain.validation.ValidationRule
import com.xinh.domain.validation.error.FormatPhoneNumberError
import com.xinh.domain.validation.error.ValidationError
import java.util.regex.Pattern

class FormatPhoneNumberRule : ValidationRule<String> {
    companion object {
        private val PATTERN = Pattern.compile("(03|07|08|09|01[2|6|8|9])+([0-9]{8})")
    }

    override fun validate(value: String?): Boolean {
        return value.isNotNullOrBlank() && PATTERN.matcher(value).matches()
    }

    override fun getError(): ValidationError {
        return FormatPhoneNumberError()
    }
}