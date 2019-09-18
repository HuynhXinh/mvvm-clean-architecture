package com.xinh.share.widget.buttonview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import com.xinh.share.R
import com.xinh.share.widget.common.BaseLinearLayout
import kotlinx.android.synthetic.main.view_button.view.*

abstract class ButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayout(context, attrs, defStyleAttr), ButtonComponent {

    private var onButtonClickListener: (() -> Unit)? = null

    private var text: String? = null
    private var isEnable: Boolean = false

    override fun getLayoutId(): Int {
        return R.layout.view_button
    }

    @CallSuper
    override fun onViewCreated() {
        tvButton.run {
            text = text

            isEnabled = isEnable

            setOnClickListener {
                onButtonClickListener?.invoke()
            }

            setBackgroundResource(getBgButtonResource())
        }
    }

    @DrawableRes
    abstract fun getBgButtonResource(): Int

    override fun setOnClickListener(listener: () -> Unit) {
        this.onButtonClickListener = listener
    }

    override fun setEnable(enable: Boolean) {
        isEnable = enable
        tvButton.isEnabled = enable
    }


    override fun getView(): View {
        return this
    }

    override fun setText(text: String) {
        this.text = text
        tvButton.text = text
    }
}