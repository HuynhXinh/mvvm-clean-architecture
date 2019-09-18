package com.xinh.domain.validation

import com.xinh.domain.validation.error.ValidationError

/**
 * Create by Mr.X on 1/25/19
 */
data class ValidationResult(var error: ValidationError, var isValid: Boolean)