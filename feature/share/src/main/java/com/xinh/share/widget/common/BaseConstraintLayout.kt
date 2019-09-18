package com.xinh.share.widget.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseConstraintLayout<V : ViewDataBinding> : ConstraintLayout {

    protected var viewBinding: V? = null

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
        val inflater = LayoutInflater.from(context)
        viewBinding = DataBindingUtil.inflate(inflater, getLayoutId(), this, true)
        onViewCreated()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun onViewCreated()
}