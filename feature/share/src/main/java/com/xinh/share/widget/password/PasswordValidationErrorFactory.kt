package com.xinh.share.widget.password

import android.content.Context
import com.xinh.domain.validation.error.RequiredFieldError
import com.xinh.domain.validation.error.ValidationError
import com.xinh.share.R
import com.xinh.share.model.InputError
import com.xinh.share.widget.password.error.LengthPasswordError

class PasswordValidationErrorFactory(private val context: Context) {
    fun getInputError(validatorError: ValidationError?): InputError? {
        return when (validatorError) {

            is RequiredFieldError -> InputError(warning = context.getString(R.string.msg_required))

            is LengthPasswordError -> InputError(
                errorMsg = context.getString(R.string.msg_password_incorrect)
            )

            else -> null
        }
    }
}