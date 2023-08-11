package com.worldpeak.chnsmilead.oa.adapter

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
import com.worldpeak.chnsmilead.oa.model.AllProcessItem
import com.worldpeak.chnsmilead.util.ReviewStatusUtil


/**
 * 全部流程
 */
class AllProcessListViewBinder(var context: Context?,val callback:(AllProcessItem)->Unit) : ItemViewBinder<AllProcessItem, AllProcessListViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_all_process_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: AllProcessItem) {
        holder.tvStatus?.text = ReviewStatusUtil.getStatusStr(entity.reviewStatus)
        holder.tvStatus?.setBackgroundResource(ReviewStatusUtil.getStatusBg(entity.reviewStatus))
        holder.tvTitle?.text = entity.title
        holder.tvNumber?.text = "表单编号：" + entity.formId
        holder.tvSchool?.text = "所属学校：" + entity.orgName
        holder.tvTime?.text = entity.dDate
        holder.itemView?.setOnClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var ivAvatar: ImageView? = null
        var tvTitle: TextView? = null
        var tvNumber: TextView? = null
        var tvStatus: TextView? = null
        var tvSchool: TextView? = null
        var tvTime: TextView? = null

        init {
            tvTitle = view.findViewById<TextView>(R.id.tvTitle)
            tvNumber = view.findViewById<TextView>(R.id.tvFileNo)
            tvStatus = view.findViewById<TextView>(R.id.tvStatus)
            tvSchool = view.findViewById<TextView>(R.id.tvSchool)
            tvTime = view.findViewById<TextView>(R.id.tvTime)
        }
    }
}