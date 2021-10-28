package com.xiaofu.jetpackdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaofu.jetpackdemo.repository.ApiRepository
import com.xiaofu.jetpackdemo.vo.WXArticle
import com.xiaofu.net.Resource
import com.xiaofu.tools.AppExecutor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val apiRepo: ApiRepository,
    private val appExecutor: AppExecutor
) : ViewModel() {

    private val _articleList = MutableStateFlow(Resource.loading<ArrayList<WXArticle>>())
    val articleList: StateFlow<Resource<ArrayList<WXArticle>>> = _articleList

    fun getWXArticle() {
        viewModelScope.launch {
            _articleList.value = apiRepo.getWXArticle()
        }
    }

}