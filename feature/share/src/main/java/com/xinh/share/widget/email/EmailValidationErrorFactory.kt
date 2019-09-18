package com.xinh.share.widget.email

import android.content.Context
import com.xinh.domain.validation.error.FormatEmailError
import com.xinh.domain.validation.error.RequiredFieldError
import com.xinh.domain.validation.error.ValidationError
import com.xinh.share.R
import com.xinh.share.model.InputError

class EmailValidationErrorFactory(private val context: Context) {
    fun getInputError(validatorError: ValidationError?): InputError? {
        return when (validatorError) {

            is RequiredFieldError -> InputError(errorMsg = context.getString(R.string.msg_required))

            is FormatEmailError -> InputError(
                warning = context.getString(R.string.txt_wrong_format),
                errorMsg = context.getString(R.string.msg_email_incorrect)
            )

            else -> null
        }
    }
}