package com.xinh.share.widget.common.clearable

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.CallSuper
import androidx.core.view.children
import com.xinh.share.extension.showOrGone
import com.xinh.share.widget.common.BaseInputLayout
import com.xinh.share.widget.common.ViewComponent

abstract class InputClearableLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseInputLayout(context, attrs, defStyleAttr) {

    private var clearableComponent: ClearableComponent? = null

    @CallSuper
    override fun onTextChanged(text: String) {
        showBtnClear(text.isNotBlank())
    }

    private fun showBtnClear(isShow: Boolean) {
        getClearableComponent()?.getView()?.showOrGone(isShow)
    }

    override fun getEndInputComponents(): List<ViewComponent>? {
        return listOf(
            ClearableView(context).apply {
                setOnClearClickListener {
                    setText("")
                }
            }
        )
    }

    private fun getClearableComponent(): ClearableComponent? {
        if (clearableComponent != null) return clearableComponent

        return viewBinding?.llEndViewHolder?.children
            ?.filterIsInstance<ClearableComponent>()?.firstOrNull()
            ?.also {
                clearableComponent = it
            }
    }

    override fun onComponentsAttached() {
        showBtnClear(false)
    }
}
