package com.worldpeak.chnsmilead.home.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.worldpeak.chnsmilead.R

class CommonConfirmDialog(
        context: Context,
        val title: String,
        val leftText: String,
        val rightText: String,
        val leftCallback: () -> Unit,
        val rightCallback: () -> Unit
) : AppCompatDialog(context, R.style.dialog_common) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_common_style)
        setCanceledOnTouchOutside(false)
        window?.attributes?.gravity = Gravity.CENTER

        findViewById<TextView>(R.id.tvTitle)?.apply {
            text = title
        }
        findViewById<TextView>(R.id.tvLeft)?.apply {
            text = leftText
            setOnClickListener {
                leftCallback.invoke()
                dismiss()
            }
        }
        findViewById<TextView>(R.id.tvRight)?.apply {
            text = rightText
            setOnClickListener {
                rightCallback.invoke()
                dismiss()
            }
        }
    }
}