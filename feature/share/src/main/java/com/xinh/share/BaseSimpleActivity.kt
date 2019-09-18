package com.xinh.share

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseSimpleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        onBindScope()
        initView()
    }

    @LayoutRes
    abstract fun getLayout(): Int

    open fun onBindScope() {}

    abstract fun initView()
}