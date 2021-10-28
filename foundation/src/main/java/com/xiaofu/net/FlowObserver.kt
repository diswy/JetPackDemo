package com.xiaofu.net

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

suspend inline fun <T> Flow<Resource<T>>.observe(listener: ResultBuilder<T>.() -> Unit) {
    val mListener = ResultBuilder<T>().also(listener)
    collect { result ->
        when (result.status) {
            Status.LOADING -> {
                mListener.loading?.invoke()
            }
            Status.SUCCESS -> {
                mListener.success?.invoke(result.data!!)
            }
            Status.ERROR -> {
                mListener.error?.invoke(result.throwable ?: Throwable("unknown error by request"))
            }
        }
    }
}

class ResultBuilder<T> {
    var loading: (() -> Unit)? = null
    var success: ((T) -> Unit)? = null
    var error: ((Throwable) -> Unit)? = null

    fun onLoading(action: () -> Unit) {
        loading = action
    }

    fun onSuccess(action: (T) -> Unit) {
        success = action
    }

    fun onError(action: (Throwable) -> Unit) {
        error = action
    }
}