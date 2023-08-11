@file:JvmName("PreventDoubleClick")

package com.worldpeak.chnsmilead.util

import android.view.View

/**
 * 防止二次点击点击监听
 */
class PreventDoubleClickListener : View.OnClickListener {
    private var clickTimeInterval: Long
    private var listener: View.OnClickListener? = null
    private var listenerLambda: ((View) -> Unit)? = null

    constructor(clickTimeInterval: Long, listener: View.OnClickListener) {
        this.clickTimeInterval = clickTimeInterval
        this.listener = listener
    }

    constructor(clickTimeInterval: Long, listener: (View) -> Unit) {
        this.clickTimeInterval = clickTimeInterval
        this.listenerLambda = listener
    }

    private var lastTime = 0L

    override fun onClick(v: View) {
        if (System.currentTimeMillis() - lastTime <= clickTimeInterval) {
            return
        }
        lastTime = System.currentTimeMillis()
        listener?.onClick(v)
        listenerLambda?.invoke(v)
    }
}

fun View.setPreventDoubleClickListener(listener: View.OnClickListener) {
    setOnClickListener(PreventDoubleClickListener(800, listener))
}

fun View.setPreventDoubleClickListener(listener: (View) -> Unit) {
    setOnClickListener(PreventDoubleClickListener(800, listener))
}

fun View.setPreventDoubleClickListener(listener: View.OnClickListener,millisecondClickInterval:Long) {
    setOnClickListener(PreventDoubleClickListener(millisecondClickInterval, listener))
}

fun View.setPreventDoubleClickListener(listener: (View) -> Unit,millisecondClickInterval:Long) {
    setOnClickListener(PreventDoubleClickListener(millisecondClickInterval, listener))
}