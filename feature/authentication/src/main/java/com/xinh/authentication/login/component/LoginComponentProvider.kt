package com.xinh.authentication.login.component

import android.content.Context
import com.xinh.share.widget.buttonview.ButtonSignInView
import com.xinh.share.widget.common.ComponentProvider
import com.xinh.share.widget.common.ViewComponent
import com.xinh.share.widget.email.InputEmailLayout
import com.xinh.share.widget.password.InputPasswordLayout

class LoginComponentProvider(private val context: Context) : ComponentProvider {
    override fun getComponents(): List<ViewComponent> {
        return listOf(
            InputEmailLayout(context),
            InputPasswordLayout(context),
            ButtonSignInView(context)
        )
    }
}