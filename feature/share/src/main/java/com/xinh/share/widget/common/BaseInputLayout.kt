package com.xinh.share.widget.common

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import com.xinh.domain.extension.isNotNullOrBlank
import com.xinh.share.R
import com.xinh.share.databinding.ViewInputLayoutBinding
import com.xinh.share.extension.convertDpToPixel
import com.xinh.share.extension.getString
import com.xinh.share.extension.onTextChanged

abstract class BaseInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseConstraintLayout<ViewInputLayoutBinding>(context, attrs, defStyleAttr),
    BaseInputComponent {

    private var _title: String? = null

    private var isPreventEventTextChange = false

    override fun getLayoutId(): Int {
        return R.layout.view_input_layout
    }

    override fun onViewCreated() {
        viewBinding?.run {

            etInput.hint = getHint()
            etInput.inputType = getInputType()
            etInput.filters = filterInput()

            isShowTitle = _title.isNotNullOrBlank()
            title = _title

            initStartViewInput(llStartViewHolder)
            etInput.onTextChanged {
                if (isPreventEventTextChange) {
                    isPreventEventTextChange = false
                } else {
                    onTextChanged(it)
                }
            }
            initEndViewInput(llEndViewHolder)

            initPadding(etInput)

            onComponentsAttached()
        }
    }

    abstract fun getHint(): String

    override fun onComponentsAttached() {

    }

    open fun filterInput(): Array<InputFilter> {
        return emptyArray()
    }

    private fun initPadding(etInput: AppCompatEditText) {
        val padding = context.convertDpToPixel(R.dimen.padding_16dp)

        if (isNeedAddPaddingStart()) {
            etInput.setPadding(padding, 0, 0, 0)
        }

        if (isNeedAddPaddingEnd()) {
            etInput.setPadding(0, 0, padding, 0)
        }
    }

    private fun isNeedAddPaddingEnd(): Boolean {
        return getEndInputComponents().isNullOrEmpty()
    }

    private fun isNeedAddPaddingStart(): Boolean {
        return getStartInputComponent() == null
    }

    abstract fun getInputType(): Int

    abstract fun onTextChanged(text: String)

    private fun initEndViewInput(llEndViewHolder: LinearLayout) {
        getEndInputComponents()?.forEach {
            llEndViewHolder.addView(it.getView())
        }
    }

    abstract fun getEndInputComponents(): List<ViewComponent>?

    private fun initStartViewInput(llStartViewHolder: LinearLayout) {
        getStartInputComponent()?.forEach {
            llStartViewHolder.addView(it.getView())
        }
    }

    abstract fun getStartInputComponent(): List<ViewComponent>?

    override fun showError(msg: String?) {
        val isShowError = msg.isNotNullOrBlank()
        viewBinding?.isShowError = isShowError
        viewBinding?.errorMsg = msg
    }

    override fun showBgError(isShow: Boolean) {
        viewBinding?.run {
            if (isShow) {
                clInput.setBackgroundResource(R.drawable.bg_white_border_red)
            } else {
                clInput.setBackgroundResource(R.drawable.bg_white_border_gray)
            }
        }
    }

    override fun hideError() {
        viewBinding?.isShowError = false
    }

    override fun setTitle(title: String) {
        _title = title
        viewBinding?.run {
            this.title = _title
            isShowTitle = _title.isNotNullOrBlank()
        }
    }

    override fun getView(): View {
        return this
    }

    override fun setText(text: String) {
        viewBinding?.etInput?.setText(text)
    }

    override fun performSetText(text: String) {
        isPreventEventTextChange = true

        setText(text)
    }

    fun getText(): String? {
        return viewBinding?.etInput?.getString()
    }

    fun preventEventTextChange() {
        isPreventEventTextChange = true
    }
}