package com.xinh.share

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<VB : ViewDataBinding> : AppCompatActivity() {

    protected var viewBinding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, getLayout())

        onBindScope()
        initView()
        intiObserveViewModel()
    }

    @LayoutRes
    abstract fun getLayout(): Int

    open fun onBindScope() {}

    abstract fun initView()

    abstract fun intiObserveViewModel()
}