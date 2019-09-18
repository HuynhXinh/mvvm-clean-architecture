package com.xinh.share.widget.common.clearable

import com.xinh.share.widget.common.ViewComponent

interface ClearableComponent : ViewComponent {

    fun setOnClearClickListener(listener: () -> Unit)

}