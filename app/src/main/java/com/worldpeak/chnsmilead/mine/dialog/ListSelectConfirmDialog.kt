package com.worldpeak.chnsmilead.mine.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.mine.model.ListItemInfo

class ListSelectConfirmDialog(
        context: Context,
        val list: List<String>? = null,
        val currentItemIndex:Int?=0,
        val touchOutSide: Boolean? = false,
        val callback: (Int) -> Unit
) : AppCompatDialog(context, R.style.dialog_common) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_list_select_confirm)
        setCanceledOnTouchOutside(touchOutSide ?: false)
        window?.attributes?.gravity = Gravity.BOTTOM

        findViewById<TextView>(R.id.tvCancel)?.apply {
            setOnClickListener {
                dismiss()
            }
        }
        val wheelView = findViewById<MyWheelView>(R.id.wheelView)
        wheelView?.setData(list,currentItemIndex?:0)

        findViewById<TextView>(R.id.tvConfirm)?.apply {
            setOnClickListener {
                callback.invoke(wheelView?.currentIndex?:-1)
                dismiss()
            }
        }
    }
}