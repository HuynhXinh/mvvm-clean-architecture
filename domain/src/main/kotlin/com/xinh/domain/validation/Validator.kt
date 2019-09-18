package com.xinh.domain.validation

import com.xinh.domain.validation.error.ValidationError

/**
 * Create by Mr.X on 1/25/19
 */
abstract class Validator<ValueType>(private var valueProvider: ValueProvider<ValueType>) :
    IValidator {

    private val validationRuleItems: MutableList<ValidationRule<ValueType>> = ArrayList()

    protected fun addRule(rule: ValidationRule<ValueType>) {
        validationRuleItems.add(rule)
    }

    init {
        this.addRules()
    }

    override fun validate(): List<ValidationResult> {
        val results: MutableList<ValidationResult> = ArrayList()
        val value: ValueType? = valueProvider.getValue()

        validationRuleItems.forEach {
            val result = ValidationResult(it.getError(), it.validate(value))
            results.add(result)
        }

        return results
    }

    abstract fun addRules()

    override fun isValid(): Boolean {
        return validate().all { it.isValid }
    }

    override fun getError(): ValidationError? {
        return getErrors().firstOrNull()
    }

    override fun getErrors(): List<ValidationError> {
        return validate().filter { !it.isValid }.map { it.error }
    }
}