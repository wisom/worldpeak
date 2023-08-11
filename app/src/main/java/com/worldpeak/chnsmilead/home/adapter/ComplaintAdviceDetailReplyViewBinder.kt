package com.worldpeak.chnsmilead.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceItem
import com.worldpeak.chnsmilead.home.model.ComplaintProposeReplyResult
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener


/**
 * 投诉建议回复
 */
class ComplaintAdviceDetailReplyViewBinder(var context: Context?, val callback: (ComplaintProposeReplyResult) -> Unit) : ItemViewBinder<ComplaintProposeReplyResult, ComplaintAdviceDetailReplyViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_complaint_advice_detail_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: ComplaintProposeReplyResult) {
        holder.ivHead?.setImageResource(R.drawable.ic_do_reply)
        holder.tvSubTitle?.text = if (entity.targetType == 1) "受理单位回复" else "转发单位回复"
        holder.tvGroup?.text = entity.schoolName
        holder.tvTime?.text = entity.createTime
        holder.tvContent?.text = entity.replyContent
        holder.itemView.setPreventDoubleClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var ivHead: ImageView? = null
        var tvSubTitle: TextView? = null
        var tvGroup: TextView? = null
        var tvTime: TextView? = null
        var tvContent: TextView? = null

        init {
            ivHead = view.findViewById<ImageView>(R.id.ivHead)
            tvSubTitle = view.findViewById<TextView>(R.id.tvSubTitle)
            tvGroup = view.findViewById<TextView>(R.id.tvGroup)
            tvTime = view.findViewById<TextView>(R.id.tvTime)
            tvContent = view.findViewById<TextView>(R.id.tvContent)
        }
    }
}