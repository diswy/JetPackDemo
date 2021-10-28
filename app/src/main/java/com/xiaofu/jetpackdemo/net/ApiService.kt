package com.xiaofu.jetpackdemo.net

import com.xiaofu.jetpackdemo.vo.BaseResponse
import com.xiaofu.jetpackdemo.vo.WXArticle
import retrofit2.http.GET

interface ApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getWXArticleList(): BaseResponse<ArrayList<WXArticle>>
}