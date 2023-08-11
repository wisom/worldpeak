package com.worldpeak.chnsmilead.mine.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.login.model.WxUserInfo
import com.worldpeak.chnsmilead.mine.model.WechatUnbindInfo
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener


/**
 */
class WechatUnbindViewBinder(var context: Context?,val itemClick:(WxUserInfo)->Unit) : ItemViewBinder<WxUserInfo, WechatUnbindViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_wechat_unbind_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: WxUserInfo) {
        holder.tvAccount?.text = entity.nickname
        holder.itemView.setPreventDoubleClickListener {
            itemClick.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var tvAccount: TextView? = null

        init {
            tvAccount = view.findViewById<TextView>(R.id.tvAccount)
        }
    }
}