package com.xiaofu.base

import androidx.viewbinding.ViewBinding

abstract class BaseBindActivity<T : ViewBinding> : BaseActivity() {
    private var _binding: T? = null

    protected open val bind get() = _binding!!

    abstract fun getBinding(): T

    override fun setView() {
        _binding = getBinding()
        setContentView(bind.root)
    }

}
