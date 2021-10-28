package com.xiaofu.jetpackdemo.repository

import com.xiaofu.jetpackdemo.App
import com.xiaofu.jetpackdemo.net.ApiService
import com.xiaofu.net.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val app: App,
    private val api: ApiService
) {

    suspend fun getWXArticle() =
        try {
            api.getWXArticleList().map()
        } catch (e: Exception) {
            app.handleExceptions(e)
            Resource.error(e)
        }

}