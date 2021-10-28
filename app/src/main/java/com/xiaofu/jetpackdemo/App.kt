package com.xiaofu.jetpackdemo

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.xiaofu.commons.toast
import dagger.hilt.android.HiltAndroidApp
import org.json.JSONException
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    val dataStore: DataStore<Preferences> by preferencesDataStore(name = "cache_demo")

    @Inject
    lateinit var gson: Gson

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun handleExceptions(t: Throwable?) {
        if (t == null) {
            toast("未知错误")
            return
        }
        if (t is CancellationException) {
            return
        }
        val errorMessage = when (t) {
            is SocketException -> "网络异常，请检查网络后重试"
            is SocketTimeoutException -> "网络超时,请稍后尝试"
            is UnknownHostException -> "网络已断开，请检查网络后重试"
            is JsonParseException -> "数据解析失败，请联系管理员"
            is JSONException -> "数据解析失败，请联系管理员"
            is ParseException -> "数据解析失败，请联系管理员"
            is HttpException -> "网络错误，请退出重试，或联系管理员"
            else -> t.message ?: "未知错误,请退出应用重试，如果仍有问题，请联系管理员"
        }
        toast(errorMessage)
    }
}