package com.worldpeak.chnsmilead.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.databinding.LayoutCommonTitlebarBinding
import com.worldpeak.chnsmilead.util.hide
import com.worldpeak.chnsmilead.util.show

/**
 * 通用标题栏
 */
class CommonTitleBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val mBinding: LayoutCommonTitlebarBinding

    init {
        mBinding = LayoutCommonTitlebarBinding.inflate(LayoutInflater.from(context), this, true)

        val typedArray = getContext().theme.obtainStyledAttributes(
                attrs,
                R.styleable.CommonTitleBar,
                defStyleAttr,
                0
        )
        val showRightIcon = typedArray.getBoolean(R.styleable.CommonTitleBar_showRightIcon, false)
        val hideLeftIcon = typedArray.getBoolean(R.styleable.CommonTitleBar_hideLeftIcon, false)
        val title = typedArray.getString(R.styleable.CommonTitleBar_title) ?: ""
        setTitle(title)
        val titleColor = typedArray.getColor(R.styleable.CommonTitleBar_titleColor, 0)
        setTitleColor(titleColor)
        val titleSize = typedArray.getDimension(R.styleable.CommonTitleBar_titleSize, 17f)
        setTitleSizePx(titleSize)
        val titleStyle = typedArray.getInt(R.styleable.CommonTitleBar_titleStyle, Typeface.NORMAL)
        setTitleStyle(titleStyle)

        val rightText = typedArray.getString(R.styleable.CommonTitleBar_rightText) ?: ""
        setRightText(rightText)
        val rightTextColor = typedArray.getColor(R.styleable.CommonTitleBar_rightTextColor, 0)
        setRightColor(rightTextColor)
        val rightTextSize = typedArray.getDimension(R.styleable.CommonTitleBar_rightTextSize, 15f)
        setRightSizePx(rightTextSize)

        if (showRightIcon) {
            showRightIcon()
        }
        if (hideLeftIcon) {
            hideBack()
        }
        val leftResId = typedArray.getResourceId(
                R.styleable.CommonTitleBar_leftResId,
                R.drawable.ic_back_black
        )
        val rightIconResId = typedArray.getResourceId(
                R.styleable.CommonTitleBar_rightIconResId,
                R.drawable.ic_share_black
        )
        setLeftImg(leftResId)
        setRightImg(rightIconResId)

        typedArray.recycle()
    }

    fun hideBack(): CommonTitleBar {
        mBinding.ivBack.hide()
        return this
    }

    fun showRightIcon(): CommonTitleBar {
        mBinding.rlRightIcon.show()
        return this
    }

    fun showRightIcon(rightIcon: Int, height: Int): CommonTitleBar {
        val lp = mBinding.rightIcon.layoutParams
        lp.height = height
        mBinding.rightIcon.layoutParams = lp
        setRightImg(rightIcon)
        mBinding.rlRightIcon.show()
        return this
    }

    /**
     * 设置返回键图标
     */
    fun setLeftImg(resId: Int): CommonTitleBar {
        mBinding.ivBack.setImageResource(resId)
        return this
    }

    fun setRightImg(resId: Int): CommonTitleBar {
        mBinding.rightIcon.setImageResource(resId)
        return this
    }

    /**
     * 设置返回点击事件
     */
    fun setBackClick(onClickListener: OnClickListener): CommonTitleBar {
        mBinding.rlBack.setOnClickListener(onClickListener)
        return this
    }

    /**
     * 设置右边更多图标点击事件
     */
    fun setRightIconClick(onRightClickListener: OnClickListener): CommonTitleBar {
        mBinding.rlRightIcon.setOnClickListener(onRightClickListener)
        return this
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String): CommonTitleBar {
        mBinding.tvTitle.setText(title)
        return this
    }

    /**
     * 设置标题
     */
    fun setTitle(titleRes: Int): CommonTitleBar {
        mBinding.tvTitle.setText(titleRes)
        return this
    }

    /**
     * 设置标题颜色
     */
    fun setTitleColor(@ColorInt color: Int): CommonTitleBar {
        mBinding.tvTitle.setTextColor(color)
        return this
    }

    /**
     * 设置标题字体样式
     * Typeface.NORMAL 正常
     * Typeface.BOLD 加粗
     * Typeface.ITALIC 斜体
     * Typeface.BOLD_ITALIC 加粗斜体
     */
    fun setTitleStyle(style: Int): CommonTitleBar {
        mBinding.tvTitle.setTypeface(null, style)
        return this
    }

    /**
     * 设置标题字体大小（单位sp）
     */
    fun setTitleSizeSp(sizeSp: Float): CommonTitleBar {
        mBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp)
        return this
    }

    /**
     * 设置标题字体大小（单位px）
     */
    fun setTitleSizePx(sizePx: Float): CommonTitleBar {
        mBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePx)
        return this
    }

    /**
     * 设置右边字体内容
     */
    fun setRightText(optText: String): CommonTitleBar {
        mBinding.tvOpt.setText(optText)
        mBinding.tvOpt.show()
        return this
    }

    /**
     * 设置右边字体内容
     */
    fun setRightText(optTextRes: Int): CommonTitleBar {
        mBinding.tvOpt.setText(optTextRes)
        mBinding.tvOpt.show()
        return this
    }

    /**
     * 设置右边字体颜色
     */
    fun setRightColor(@ColorInt color: Int): CommonTitleBar {
        mBinding.tvOpt.setTextColor(color)
        mBinding.tvOpt.show()
        return this
    }

    /**
     * 设置右边字体大小（单位sp）
     */
    fun setRightSizeSp(sizeSp: Float): CommonTitleBar {
        mBinding.tvOpt.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp)
        mBinding.tvOpt.show()
        return this
    }

    /**
     * 设置右边字体大小（单位px）
     */
    fun setRightSizePx(sizePx: Float): CommonTitleBar {
        mBinding.tvOpt.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePx)
        mBinding.tvOpt.show()
        return this
    }

    /**
     * 设置右边字体点击事件
     */
    fun setRightClick(onClickListener: OnClickListener): CommonTitleBar {
        mBinding.tvOpt.setOnClickListener(onClickListener)
        return this
    }

    fun addRightView(rightView: View): CommonTitleBar {
        mBinding.llRight.addView(rightView)
        return this
    }

}