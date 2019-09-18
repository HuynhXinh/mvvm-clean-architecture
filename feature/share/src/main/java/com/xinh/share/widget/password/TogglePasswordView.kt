package com.xinh.share.widget.password

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.xinh.share.R
import com.xinh.share.widget.common.BaseLinearLayout
import com.xinh.share.widget.password.component.TogglePasswordComponent
import kotlinx.android.synthetic.main.view_toggle_password.view.*

class TogglePasswordView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseLinearLayout(context, attrs, defStyleAttr), TogglePasswordComponent {

    private var isPasswordShown = false
    private var listener: (Boolean) -> Unit = {}

    override fun getLayoutId(): Int {
        return R.layout.view_toggle_password
    }

    override fun onViewCreated() {
        ivTogglePassword.setOnClickListener {
            toggle()
        }
    }

    private fun toggle() {
        isPasswordShown = !isPasswordShown
        listener(isPasswordShown)
        setIconViewPassword()
    }

    private fun setIconViewPassword() {
        val icon = if (isPasswordShown) R.drawable.ic_seen else R.drawable.ic_unseen
        ivTogglePassword.setImageResource(icon)
    }

    override fun setOnTogglePasswordChangeListener(listener: (Boolean) -> Unit) {
        this.listener = listener
    }

    override fun getView(): View {
        return this
    }
}