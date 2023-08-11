package com.worldpeak.chnsmilead.mine.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.mine.adapter.ListSelectViewBinder
import com.worldpeak.chnsmilead.mine.model.ListItemInfo

class ListSelectDialog(context: Context, val list: List<ListItemInfo>? = null,val touchOutSide:Boolean?=false) : AppCompatDialog(context, R.style.dialog_common) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_list_select)
        setCanceledOnTouchOutside(touchOutSide?:false)
        window?.attributes?.gravity = Gravity.BOTTOM

        findViewById<RecyclerView>(R.id.rvList)?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = MultiTypeAdapter().apply {
                register(ListItemInfo::class.java, ListSelectViewBinder() {
                    dismiss()
                })
                if (list != null) {
                    items = list
                }
            }
        }
        findViewById<TextView>(R.id.tvCancel)?.apply {
            setOnClickListener {
                dismiss()
            }
        }
    }
}