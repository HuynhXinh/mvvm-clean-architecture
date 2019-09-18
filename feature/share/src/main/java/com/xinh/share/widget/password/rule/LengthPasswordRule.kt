package com.xinh.share.widget.password.rule

import com.xinh.domain.validation.ValidationRule
import com.xinh.domain.validation.error.ValidationError
import com.xinh.share.extension.safe
import com.xinh.share.widget.password.error.LengthPasswordError

class LengthPasswordRule : ValidationRule<String> {
    companion object {
        const val MIN_LENGTH = 8
    }

    override fun validate(value: String?): Boolean {
        return value?.length.safe() >= MIN_LENGTH
    }

    override fun getError(): ValidationError {
        return LengthPasswordError()
    }

}