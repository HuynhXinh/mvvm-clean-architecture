package com.xinh.share.widget.buttonview

import android.content.Context
import android.util.AttributeSet
import com.xinh.share.R

open class GreenButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ButtonView(context, attrs, defStyleAttr) {
    override fun getBgButtonResource(): Int {
        return R.drawable.bg_button_green_corner_selector
    }
}