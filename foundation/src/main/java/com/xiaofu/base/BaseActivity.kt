package com.xiaofu.base

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    abstract fun setView()

    abstract fun initialize()

    protected open fun keepScreenOn() = false
    protected open fun statusBarLightMode() = false
    protected open fun fullScreenMode() = false
    protected open fun translucentMode() = false
    protected open fun statusBarColor() = Color.TRANSPARENT
    protected open fun bindListener() {}
    protected open suspend fun taskScope() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        systemWindowConfig()
        setView()
        initialize()
        bindListener()
        lifecycleScope.launch {
            taskScope()
        }
    }

    @Suppress("DEPRECATION")
    private fun systemWindowConfig() {
        ViewCompat.getWindowInsetsController(window.decorView)?.let { controller ->
            if (keepScreenOn())
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

            if (fullScreenMode())
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )

            if (translucentMode())
                WindowCompat.setDecorFitsSystemWindows(window, false)

            controller.isAppearanceLightStatusBars = statusBarLightMode()

//            window.statusBarColor = statusBarColor()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    /**
     * 使用主题颜色作为状态栏颜色
     */
    protected open fun initStatusBarColor() {
        val defaultColor = 0x00000000
        val attrsArray = intArrayOf(android.R.attr.colorPrimary)
        val typedArray = obtainStyledAttributes(attrsArray)
        val accentColor = typedArray.getColor(0, defaultColor)
        window.statusBarColor = accentColor
        typedArray.recycle()
    }

}