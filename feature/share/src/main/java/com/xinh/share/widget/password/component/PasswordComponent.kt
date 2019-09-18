package com.xinh.share.widget.password.component

import com.xinh.share.widget.common.ViewComponent
import com.xinh.share.widget.common.OnValidatingListener
import com.xinh.share.widget.common.ValidationComponent

interface PasswordComponent : ViewComponent,
    ValidationComponent,
    OnValidatingListener {
    fun getPassword(): String
}