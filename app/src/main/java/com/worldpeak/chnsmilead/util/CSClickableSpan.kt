package com.worldpeak.chnsmilead.util

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.annotation.ColorInt

/**
 * 自定义 ClickableSpan
 * @param textColor 可点击标记文字颜色
 * @param clickListener 点击时间监听
 */
class CSClickableSpan (@param:ColorInt private val textColor: Int,
                       private val clickListener: View.OnClickListener?) : ClickableSpan() {
    override fun onClick(widget: View) {
        clickListener?.onClick(widget)
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)

        ds.color = textColor // 字体颜色（前景色）
        ds.bgColor = Color.TRANSPARENT  // 背景颜色
        ds.linkColor = textColor // 链接颜色
        ds.isUnderlineText = false // 是否显示下划线
        // 这里还可以配置其他绘制样式，比如下划线的粗细（如果启用下划线）、字体等等
    }
}
