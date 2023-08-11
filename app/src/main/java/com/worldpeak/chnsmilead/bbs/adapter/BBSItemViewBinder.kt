package com.worldpeak.chnsmilead.bbs.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drakeet.multitype.ItemViewBinder
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.bbs.model.BBSItem
import com.worldpeak.chnsmilead.util.hide
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener
import com.worldpeak.chnsmilead.util.show


/**
 * 专家论坛
 */
class BBSItemViewBinder(var context: Context?, val callback: (BBSItem) -> Unit) : ItemViewBinder<BBSItem, BBSItemViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_area_dynamic_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: BBSItem) {
        holder.ivCover?.hide()
        if (!entity.topImg.isNullOrEmpty()) {
            holder.ivCover?.show()
            holder.ivCover?.let { Glide.with(holder.itemView.context).load(entity.topImg).into(it) }
        }
        holder.tvTitle?.text = entity.title
        holder.tvContent?.text = entity.intro
        holder.tvGroup?.text = entity.author
        holder.tvTime?.text = entity.publishTime
        holder.itemView.setPreventDoubleClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var ivCover: ImageView? = null
        var tvTitle: TextView? = null
        var tvContent: TextView? = null
        var tvGroup: TextView? = null
        var tvTime: TextView? = null

        init {
            ivCover = view.findViewById<ImageView>(R.id.ivCover)
            tvTitle = view.findViewById<TextView>(R.id.tvTitle)
            tvContent = view.findViewById<TextView>(R.id.tvContent)
            tvGroup = view.findViewById<TextView>(R.id.tvGroup)
            tvTime = view.findViewById<TextView>(R.id.tvTime)
        }
    }
}