package com.xinh.share.widget.email

import com.xinh.domain.validation.Validator
import com.xinh.domain.validation.ValueProvider
import com.xinh.domain.validation.rule.FormatEmailRule
import com.xinh.domain.validation.rule.RequiredFieldRule

class EmailValidator(email: String?) : Validator<String>(object : ValueProvider<String> {
    override fun getValue(): String? {
        return email
    }
}) {

    override fun addRules() {
        addRule(RequiredFieldRule())
        addRule(FormatEmailRule())
    }
}