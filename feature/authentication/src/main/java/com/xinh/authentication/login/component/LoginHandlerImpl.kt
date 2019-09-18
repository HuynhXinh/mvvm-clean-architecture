package com.xinh.authentication.login.component

import android.content.Context
import android.widget.LinearLayout
import com.xinh.domain.param.LoginParam
import com.xinh.share.widget.buttonview.ButtonComponent
import com.xinh.share.widget.common.OnValidatingListener
import com.xinh.share.widget.common.ValidationComponent
import com.xinh.share.widget.common.ViewComponent
import com.xinh.share.widget.email.EmailComponent
import com.xinh.share.widget.password.component.PasswordComponent

class LoginHandlerImpl : LoginHandler {

    private var components: List<ViewComponent>? = null

    private var listener: ((LoginParam) -> Unit)? = null

    override fun init(context: Context, root: LinearLayout) {
        components = LoginComponentProvider(context).getComponents()
        components?.forEach {
            root.addView(it.getView(), it.getLinearLayoutParams(context))
        }

        initBtnSignIn()

        initOnValidating()
    }

    private fun initOnValidating() {
        components?.filterIsInstance<OnValidatingListener>()
            ?.forEach {
                it.setOnValidatingListener {
                    getBtnSignInComponent()?.setEnable(isValid())
                }
            }
    }

    private fun isValid(): Boolean {
        return components?.filterIsInstance<ValidationComponent>()
            ?.all { it.isValid() } == true
    }

    private fun initBtnSignIn() {
        getBtnSignInComponent()?.run {
            setOnClickListener {
                listener?.invoke(getLoginParam())
            }
        }
    }

    private fun getLoginParam(): LoginParam {
        return LoginParam(
            email = getEmail(),
            password = getPassword()
        )
    }

    private fun getPassword(): String? {
        return components?.filterIsInstance<PasswordComponent>()?.firstOrNull()?.getPassword()
    }

    private fun getEmail(): String? {
        return components?.filterIsInstance<EmailComponent>()?.firstOrNull()?.getEmail()
    }

    private fun getBtnSignInComponent(): ButtonComponent? {
        return components?.filterIsInstance<ButtonComponent>()?.firstOrNull()
    }

    override fun setOnLoginClickListener(listener: (LoginParam) -> Unit) {
        this.listener = listener
    }
}