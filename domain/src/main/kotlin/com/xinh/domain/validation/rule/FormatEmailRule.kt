package com.xinh.domain.validation.rule

import com.xinh.domain.extension.isNotNullOrBlank
import com.xinh.domain.validation.ValidationRule
import com.xinh.domain.validation.error.FormatEmailError
import com.xinh.domain.validation.error.ValidationError
import java.util.regex.Pattern

class FormatEmailRule : ValidationRule<String> {
    companion object {
        private val PATTERN = Pattern.compile(
            ("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+")
        )
    }

    override fun validate(value: String?): Boolean {
        return value.isNotNullOrBlank() && PATTERN.matcher(value).matches()
    }

    override fun getError(): ValidationError {
        return FormatEmailError()
    }
}