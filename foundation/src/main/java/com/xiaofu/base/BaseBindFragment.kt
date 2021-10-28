package com.xiaofu.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BaseBindFragment<T : ViewBinding> : BaseFragment() {

    private var _binding: T? = null

    protected open val bind get() = _binding!!

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun setView(): Int = 0// 已生成视图绑定无需资源ID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getBinding(inflater, container)
        return bind.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}