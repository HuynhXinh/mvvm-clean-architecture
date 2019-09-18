package com.xinh.share.widget.password

import com.xinh.domain.validation.Validator
import com.xinh.domain.validation.ValueProvider
import com.xinh.domain.validation.rule.RequiredFieldRule
import com.xinh.share.widget.password.rule.LengthPasswordRule

class PasswordValidator(password: String?) : Validator<String>(object : ValueProvider<String> {
    override fun getValue(): String? {
        return password
    }
}) {

    override fun addRules() {
        addRule(RequiredFieldRule())
        addRule(LengthPasswordRule())
    }
}