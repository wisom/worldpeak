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
import com.worldpeak.chnsmilead.oa.viewmodel.DocumentApproveInfo


/**
 */
class DocumentApprovalDetailViewBinder(var context: Context?) : ItemViewBinder<DocumentApproveInfo, DocumentApprovalDetailViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_document_approve_process,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: DocumentApproveInfo) {
        holder.tvSimple?.text = entity.approveName?.subSequence(0, 1)
        holder.tvName?.text = entity.approveName
        holder.tvRole?.text = entity.orgName
        holder.tvTime?.text = entity.approveDate
        holder.tvContent?.text = entity.approveRemark
        //（0等待、1待批/待读、2已批/已读、3拒批）
        val checkRes = when (entity.status) {
            2 -> {
                R.drawable.ic_status_check
            }
            3 -> {
                R.drawable.ic_status_reject
            }
            else -> {
                R.drawable.ic_status_wait
            }
        }
        holder.ivCheck?.setImageResource(checkRes)
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var tvSimple: TextView? = null
        var ivCheck: ImageView? = null
        var tvName: TextView? = null
        var tvRole: TextView? = null
        var tvTime: TextView? = null
        var tvContent: TextView? = null

        init {
            tvSimple = view.findViewById<TextView>(R.id.tvSimple)
            ivCheck = view.findViewById<ImageView>(R.id.ivCheck)
            tvName = view.findViewById<TextView>(R.id.tvName)
            tvRole = view.findViewById<TextView>(R.id.tvRole)
            tvTime = view.findViewById<TextView>(R.id.tvTime)
            tvContent = view.findViewById<TextView>(R.id.tvContent)
        }
    }
}