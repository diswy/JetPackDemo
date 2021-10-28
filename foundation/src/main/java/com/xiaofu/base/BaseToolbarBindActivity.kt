package com.xiaofu.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.xiaofu.R

abstract class BaseToolbarBindActivity<T : ViewBinding> : BaseActivity() {
    private var _binding: T? = null

    protected open val bind get() = _binding!!

    abstract fun getBinding(): T

    @SuppressLint("WrongViewCast")
    protected open fun getContainer(): ViewGroup {
        return findViewById(R.id.base_container)
    }

    override fun setView() {
        setContentView(R.layout.activity_base_toolbar_parent)
        _binding = getBinding()
    }
}