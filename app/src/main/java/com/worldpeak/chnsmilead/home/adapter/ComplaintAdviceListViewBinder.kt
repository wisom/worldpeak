package com.worldpeak.chnsmilead.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceItem
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener


/**
 */
class ComplaintAdviceListViewBinder(var context: Context?,val callback:(ComplaintAdviceItem)->Unit) : ItemViewBinder<ComplaintAdviceItem, ComplaintAdviceListViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_complaint_advice_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: ComplaintAdviceItem) {
        holder.tvTitle?.text = entity.title
        holder.tvContent?.text = entity.content
        holder.tvTag?.text = entity.complaintProposeName
        holder.tvTime?.text = entity.createTime
        holder.itemView.setPreventDoubleClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView? = null
        var tvContent: TextView? = null
        var tvTag: TextView? = null
        var tvTime: TextView? = null

        init {
            tvTitle = view.findViewById<TextView>(R.id.tvTitle)
            tvContent = view.findViewById<TextView>(R.id.tvContent)
            tvTag = view.findViewById<TextView>(R.id.tvTag)
            tvTime = view.findViewById<TextView>(R.id.tvTime)
        }
    }
}