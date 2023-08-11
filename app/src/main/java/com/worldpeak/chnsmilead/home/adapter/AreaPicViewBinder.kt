package com.worldpeak.chnsmilead.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drakeet.multitype.ItemViewBinder
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.home.model.DynamicItem
import com.worldpeak.chnsmilead.util.DensityUtils


/**
 * 区域图片
 */
class AreaPicViewBinder(var context: Context?,val callback:(DynamicItem)->Unit) : ItemViewBinder<DynamicItem, AreaPicViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_school_pic_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: DynamicItem) {
        holder.ivCover?.let { Glide.with(holder.itemView.context).load(entity.topImg).into(it) }
        holder.tvSchool?.text = entity.title
        holder.itemView.setOnClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var ivCover: ImageView? = null
        var tvSchool: TextView? = null

        init {
            val displayWidth = DensityUtils.getDisplayWidth(view.context)
            val perWith = (displayWidth - DensityUtils.dip2px(view.context, 16 * 2f + 12 * 2)) / 3
            val lp = ConstraintLayout.LayoutParams(perWith, perWith)
            ivCover?.layoutParams = lp
            ivCover = view.findViewById<ImageView>(R.id.ivCover)
            tvSchool = view.findViewById<TextView>(R.id.tvSchool)
        }
    }
}