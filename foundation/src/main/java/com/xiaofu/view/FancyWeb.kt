package com.xiaofu.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebViewClient


@Suppress("unused")
class FancyWeb @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), LifecycleObserver {

    private var mAgentWeb: AgentWeb? = null

    private val jsInterfaceMap = HashMap<String, Any>()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun lifeOnPause() {
        mAgentWeb?.webLifeCycle?.onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun lifeOnResume() {
        mAgentWeb?.webLifeCycle?.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun lifeOnDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
    }

    /**
     * 获取js交互配置，在load方法之前调用
     */
    fun getInterfaceConfig() = jsInterfaceMap

    fun load(activity: Activity, url: String, webViewClient: WebViewClient? = null) {
        mAgentWeb = AgentWeb.with(activity)
            .setAgentWebParent(this, LayoutParams(-1, -1))
            .closeIndicator()
            .setWebViewClient(webViewClient)
            .createAgentWeb()
            .ready()
            .go(url)

        mAgentWeb!!.webCreator.webView.setDownloadListener { downloadPath, _, _, _, _ ->
            downloadByBrowser(downloadPath)
        }

        jsInterfaceMap.forEach { (key, obj) ->
            mAgentWeb!!.jsInterfaceHolder.addJavaObject(key, obj)
        }
    }

    fun load(fragment: Fragment, url: String, webViewClient: WebViewClient? = null) {
        mAgentWeb = AgentWeb.with(fragment)
            .setAgentWebParent(this, LayoutParams(-1, -1))
            .closeIndicator()
            .setWebViewClient(webViewClient)
            .createAgentWeb()
            .ready()
            .go(url)

        mAgentWeb!!.webCreator.webView.setDownloadListener { downloadPath, _, _, _, _ ->
            downloadByBrowser(downloadPath)
        }

        jsInterfaceMap.forEach { (key, obj) ->
            mAgentWeb!!.jsInterfaceHolder.addJavaObject(key, obj)
        }
    }

    private fun downloadByBrowser(path: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse(path)
        context.startActivity(intent)
    }

    fun canGoBack(): Boolean {
        return mAgentWeb?.webCreator?.webView?.canGoBack() ?: false
    }

    fun back() {
        mAgentWeb?.back()
    }

}