package com.xinh.share.widget.password.component

import com.xinh.share.widget.common.ViewComponent

interface TogglePasswordComponent : ViewComponent {
    fun setOnTogglePasswordChangeListener(listener: (Boolean) -> Unit = {})
}