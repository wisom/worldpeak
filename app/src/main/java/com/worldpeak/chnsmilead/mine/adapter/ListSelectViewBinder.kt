package com.worldpeak.chnsmilead.mine.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.mine.model.ListItemInfo
import com.worldpeak.chnsmilead.mine.model.WechatUnbindInfo
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener


/**
 */
class ListSelectViewBinder(val click:()->Unit) : ItemViewBinder<ListItemInfo, ListSelectViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_list_select_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: ListItemInfo) {
        holder.tvContent?.text = entity.content
        holder.tvContent?.setTextColor(Color.parseColor(entity.color ?: "#F55F4E"))
        holder.itemView.setPreventDoubleClickListener {
            entity.click?.onClick(holder.itemView)
            click.invoke()
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var tvContent: TextView? = null

        init {
            tvContent = view.findViewById<TextView>(R.id.tvContent)
        }
    }
}