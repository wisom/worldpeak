package com.worldpeak.chnsmilead.oa.adapter.notice

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
import com.worldpeak.chnsmilead.home.model.CommonAccessory
import com.worldpeak.chnsmilead.util.AttachUtil


/**
 * 通知下发文件
 */
class NoticeDeliveryListFileViewBinder(var context: Context?, val callback: (CommonAccessory) -> Unit) : ItemViewBinder<CommonAccessory, NoticeDeliveryListFileViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_notice_delivery_file,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: CommonAccessory) {
        holder.ivFile?.setImageResource(AttachUtil.getImageRes(entity.fileOriginName))
        holder.tvFile?.text = entity.fileOriginName
        holder.itemView.setOnClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var ivFile: ImageView? = null
        var tvFile: TextView? = null

        init {
            ivFile = view.findViewById<ImageView>(R.id.ivFile)
            tvFile = view.findViewById<TextView>(R.id.tvFile)
        }
    }
}