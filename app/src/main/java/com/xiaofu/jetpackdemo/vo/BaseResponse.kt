package com.xiaofu.jetpackdemo.vo

import com.xiaofu.net.Resource

data class BaseResponse<T>(
    val errorMsg: String,
    val errorCode: Int,
    val data: T?
) {
    fun map(): Resource<T> {
        return if (errorCode == 0) {
            Resource.success(data)
        } else {
            Resource.error(Throwable(errorMsg))
        }
    }
}