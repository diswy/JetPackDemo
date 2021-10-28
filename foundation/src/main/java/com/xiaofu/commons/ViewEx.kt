package com.xiaofu.commons

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide


fun View.showOrGone(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun View.showOrInvisible(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.INVISIBLE
}

fun ImageView.load(url: String?) {
    Glide.with(this).load(url).into(this)
}

fun TextView.setDrawableStart(context: Context, @DrawableRes icRes: Int) {
    ContextCompat.getDrawable(context, icRes)?.let { ic ->
        ic.setBounds(0, 0, ic.minimumWidth, ic.minimumHeight)
        this.setCompoundDrawables(ic, null, null, null)
    }
}

fun TextView.setDrawableTop(drawable: Drawable) {
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, drawable, null, null)
}