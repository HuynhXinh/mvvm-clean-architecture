package com.xinh.share.widget.email

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import com.xinh.domain.extension.safe
import com.xinh.share.R
import com.xinh.share.extension.getString
import com.xinh.share.model.InputError
import com.xinh.share.widget.inputlayout.InputFilterConstants.SPACE_INPUT_FILTER
import com.xinh.share.widget.common.ViewComponent
import com.xinh.share.widget.common.clearable.InputClearableLayout
import com.xinh.share.widget.common.icon.IconEmailView

class InputEmailLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : InputClearableLayout(context, attrs, defStyleAttr), EmailComponent {

    private var onValidatingListener: (() -> Unit)? = null

    override fun getHint(): String {
        return getString(R.string.hint_your_email)
    }

    override fun getInputType(): Int {
        return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }

    override fun filterInput(): Array<InputFilter> {
        return arrayOf(SPACE_INPUT_FILTER)
    }

    override fun onTextChanged(text: String) {
        super.onTextChanged(text)
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
        return EmailValidationErrorFactory(context).getInputError(getEmailValidator().getError())
    }

    override fun getStartInputComponent(): List<ViewComponent>? {
        return listOf(IconEmailView(context))
    }

    override fun getEmail(): String {
        return getText().safe()
    }

    override fun isValid(): Boolean {
        return getEmailValidator().isValid()
    }

    private fun getEmailValidator(): EmailValidator {
        return EmailValidator(getEmail())
    }

    override fun setOnValidatingListener(onValidatingListener: () -> Unit) {
        this.onValidatingListener = onValidatingListener
    }
}
