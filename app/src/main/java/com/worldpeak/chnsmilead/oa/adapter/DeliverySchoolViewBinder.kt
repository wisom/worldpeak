package com.worldpeak.chnsmilead.oa.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.oa.model.SchoolNotifyInfo


/**
 * 通知下发
 */
class DeliverySchoolViewBinder(var context: Context?, val callback: (SchoolNotifyInfo) -> Unit) : ItemViewBinder<SchoolNotifyInfo, DeliverySchoolViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_delivery_detail_group_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: SchoolNotifyInfo) {
        holder.tvGroup?.text = entity.schoolName
        holder.tvUnread?.text = (entity.notConfirmCount?:0).toString()
        holder.itemView.setOnClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var tvGroup: TextView? = null
        var tvUnread: TextView? = null

        init {
            tvGroup = view.findViewById<TextView>(R.id.tvGroup)
            tvUnread = view.findViewById<TextView>(R.id.tvUnread)
        }
    }
}