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
import com.worldpeak.chnsmilead.home.model.CommonAccessory
import com.worldpeak.chnsmilead.util.AttachUtil


/**
 * 通知下发文件
 */
class DeliveryDetailFileLongViewBinder(var context: Context?, val callback: (CommonAccessory) -> Unit) : ItemViewBinder<CommonAccessory, DeliveryDetailFileLongViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_has_reply_file_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: CommonAccessory) {
        holder.ivAttach?.setImageResource(AttachUtil.getImageRes(entity.fileOriginName))
        holder.tvAttach?.text = entity.fileOriginName
        holder.itemView.setOnClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var ivAttach: ImageView? = null
        var tvAttach: TextView? = null

        init {
            ivAttach = view.findViewById<ImageView>(R.id.ivAttach)
            tvAttach = view.findViewById<TextView>(R.id.tvAttach)
        }
    }
}