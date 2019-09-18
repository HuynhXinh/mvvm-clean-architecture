package com.xinh.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    protected var viewBinding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding?.run {
            lifecycleOwner = viewLifecycleOwner
        }
        onBindScope()
        initView()
        intiObserveViewModel()
    }

    @LayoutRes
    abstract fun getLayout(): Int

    open fun onBindScope() {}

    abstract fun initView()

    abstract fun intiObserveViewModel()

    override fun onDestroy() {
        viewBinding = null

        super.onDestroy()
    }
}