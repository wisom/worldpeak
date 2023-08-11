package com.worldpeak.chnsmilead.util

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE

}

fun View.invisible() {
    this.visibility = View.INVISIBLE

}

val Number.dp: Int get() = (toInt() * Resources.getSystem().displayMetrics.density).toInt()

val Number.px2dp: Int get() = (toInt() / Resources.getSystem().displayMetrics.density).toInt()

/**
 * 判断view是否超过50%可见
 */
fun View.isShade(): Boolean {
    if (this.visibility != View.VISIBLE) {
        return true
    }

    var currentView = this

    val currentViewRect = Rect()
    val isCoverd = currentView.getGlobalVisibleRect(currentViewRect)
    // 如果在屏幕外肯定是不可见的
    if (!isCoverd) {
        return true
    }

    if (currentViewRect.width() * currentViewRect.height() <= this.measuredHeight * this.measuredWidth / 2) {
        // 如果移出屏幕的面积大于 50% 则认为被遮罩了
        return true
    }
    // 记录下被移出屏幕外的面积
    val outScreenArea = this.measuredHeight * this.measuredWidth - currentViewRect.width() * currentViewRect.height()
    // 循环查找父布局及兄弟布局
    while (currentView.parent is ViewGroup) {
        val currentParent = currentView.parent as ViewGroup

        if (currentParent.visibility != View.VISIBLE)
            return true

        val start = indexOfViewInParent(currentView, currentParent)
        for (i in start + 1 until currentParent.childCount) {
            val otherView = currentParent.getChildAt(i)
            // 这里主要是为了排除 invisible 属性标记的 view
            if (otherView.visibility != View.VISIBLE) {
                break
            }
            val viewRect = Rect()
            this.getGlobalVisibleRect(viewRect)
            val otherViewRect = Rect()
            otherView.getGlobalVisibleRect(otherViewRect)
            // 这个方法用来检测两个区域是否重叠，并且如果重叠的话
            // 就将当前 Rect 修改为重叠的区域
            if (otherViewRect.intersect(viewRect)) {
                if ((outScreenArea + otherViewRect.width() * otherViewRect.height())
                        >= viewRect.width() * viewRect.height() / 2)
                // 表示相交区域 + 屏幕外的区域 大于 50% 则也认为被遮罩了
                    return true
            }
        }
        currentView = currentParent
    }
    return false
}

private fun indexOfViewInParent(view: View, parent: ViewGroup): Int {
    var index = 0
    // 查找出应该从第几个子 view 开始 参考事件分发机制从用户可以见到的最上层开始分发，
    // 最上层的子 view，index 值总是更大
    while (index < parent.childCount) {
        if (parent.getChildAt(index) == view)
            break
        index++
    }
    return index
}