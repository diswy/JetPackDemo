package com.xiaofu.jetpackdemo

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaofu.base.BaseBindActivity
import com.xiaofu.commons.toast
import com.xiaofu.jetpackdemo.adapter.MyAdapter
import com.xiaofu.jetpackdemo.databinding.ActivityMainBinding
import com.xiaofu.jetpackdemo.viewmodel.ApiViewModel
import com.xiaofu.net.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindActivity<ActivityMainBinding>() {

    private val apiViewModel by viewModels<ApiViewModel>()
    private lateinit var adapter: MyAdapter

    override fun getBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initialize() {
        adapter = MyAdapter(this)
        bind.recycler.layoutManager = LinearLayoutManager(this)
        bind.recycler.adapter = adapter

        apiViewModel.getWXArticle() // 请求网络
    }

    /**
     * 本Demo使用了kotlin Flow 需要协程作用域环境
     * 可使用LiveData替代,则不需要
     */
    override suspend fun taskScope() {
        // DSL语法，可根据需要灵活配置回调 提高代码可读性
        apiViewModel.articleList.observe {
            onLoading {
                toast("数据请求中...")
            }
            onSuccess {
                adapter.setData(it)
            }
//            onError {}
        }
    }
}