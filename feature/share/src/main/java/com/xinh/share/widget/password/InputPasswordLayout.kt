package com.xinh.share.widget.password

import android.content.Context
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import com.xinh.share.R
import com.xinh.share.extension.getString
import com.xinh.domain.extension.safe
import com.xinh.share.model.InputError
import com.xinh.share.widget.common.BaseInputLayout
import com.xinh.share.widget.common.ViewComponent
import com.xinh.share.widget.common.icon.IconPasswordView
import com.xinh.share.widget.password.component.PasswordComponent
import com.xinh.share.widget.password.component.TogglePasswordComponent

class InputPasswordLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseInputLayout(context, attrs, defStyleAttr), PasswordComponent {

    private var onValidatingListener: (() -> Unit)? = null

    override fun getHint(): String {
        return getString(R.string.hint_your_password)
    }

    override fun getInputType(): Int {
        return InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    override fun onTextChanged(text: String) {
        validate()
        onValidatingListener?.invoke()
    }

    private fun validate() {
        viewBinding?.run {
            if (isValid()) {
                showBgError(false)

                hideError()
            } else {
                showBgError(true)

                showError(getInputError()?.errorMsg)
            }
        }
    }

    private fun getInputError(): InputError? {
        return PasswordValidationErrorFactory(context).getInputError(getPasswordValidator().getError())
    }

    override fun getStartInputComponent(): List<ViewComponent>? {
        return listOf(IconPasswordView(context))
    }

    override fun getEndInputComponents(): List<ViewComponent>? {
        return listOf(getTogglePasswordComponent())
    }

    private fun getTogglePasswordComponent(): TogglePasswordComponent {
        return TogglePasswordView(context).apply {
            setOnTogglePasswordChangeListener {
                preventEventTextChange()

                toggleViewPassword(it)
            }
        }
    }

    private fun toggleViewPassword(isPasswordShown: Boolean) {
        viewBinding?.etInput?.run {
            if (isPasswordShown) {
                transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                transformationMethod = PasswordTransformationMethod.getInstance()
            }

            setSelection(this.getString().length)
        }
    }

    override fun getPassword(): String {
        return getText().safe()
    }

    override fun isValid(): Boolean {
        return getPasswordValidator().isValid()
    }

    private fun getPasswordValidator(): PasswordValidator {
        return PasswordValidator(getPassword())
    }

    override fun setOnValidatingListener(onValidatingListener: () -> Unit) {
        this.onValidatingListener = onValidatingListener
    }
}
