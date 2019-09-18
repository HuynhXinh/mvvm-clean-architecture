package com.xinh.share.widget.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.LayoutRes

abstract class BaseLinearLayout : LinearLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(getLayoutId(), this, true)

        orientation = getCustomOrientation()

        onViewCreated()
    }

    private fun getCustomOrientation(): Int {
        return VERTICAL
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun onViewCreated()
}