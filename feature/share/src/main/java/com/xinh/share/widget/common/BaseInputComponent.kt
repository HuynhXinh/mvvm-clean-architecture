package com.xinh.share.widget.common

import com.xinh.share.widget.inputlayout.BackgroundErrorComponent
import com.xinh.share.widget.inputlayout.InputErrorComponent

interface BaseInputComponent : ViewComponent,
    TitleComponent,
    InputErrorComponent,
    BackgroundErrorComponent {

    fun onComponentsAttached()

    fun setText(text: String)

    fun performSetText(text: String)
}