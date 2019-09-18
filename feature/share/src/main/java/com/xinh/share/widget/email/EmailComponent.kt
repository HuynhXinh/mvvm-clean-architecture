package com.xinh.share.widget.email

import com.xinh.share.widget.common.OnValidatingListener
import com.xinh.share.widget.common.ValidationComponent
import com.xinh.share.widget.common.ViewComponent

interface EmailComponent : ViewComponent,
    ValidationComponent,
    OnValidatingListener {
    fun getEmail(): String
}