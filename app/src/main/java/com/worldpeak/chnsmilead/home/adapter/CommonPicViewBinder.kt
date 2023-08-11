package com.worldpeak.chnsmilead.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore
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
import com.worldpeak.chnsmilead.home.model.PicInfo
import com.worldpeak.chnsmilead.util.DensityUtils
import com.worldpeak.chnsmilead.util.hide
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener
import com.worldpeak.chnsmilead.util.show


/**
 * 通用图片
 */
class CommonPicViewBinder(var context: Context?, val addClick: (PicInfo) -> Unit, val delClick: (PicInfo) -> Unit) : ItemViewBinder<PicInfo, CommonPicViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_common_pic_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: PicInfo) {
        if (entity.isAdd == true) {
            holder.clAdd?.show()
            holder.clAdd?.setPreventDoubleClickListener {
                addClick.invoke(entity)
            }
        } else {
            val displayWidth = DensityUtils.getDisplayWidth(holder.itemView.context)
            val perWith = (displayWidth - DensityUtils.dip2px(holder.itemView.context, 32 * 2f + 12 * 2)) / 3
            val lp = ConstraintLayout.LayoutParams(perWith, perWith)
            holder.ivPic?.layoutParams = lp
            MediaStore.Images.Media.getBitmap(holder.itemView.context.contentResolver, entity.uri)?.let { bitmap ->
                holder.ivPic?.setImageBitmap(bitmap)
            }
            holder.clAdd?.hide()
            holder.rlRemove?.setPreventDoubleClickListener {
                delClick.invoke(entity)
            }
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var clAdd: View? = null
        var rlRemove: View? = null
        var ivPic: ImageView? = null
        var tvSchool: TextView? = null

        init {
            clAdd = view.findViewById<View>(R.id.clAdd)
            rlRemove = view.findViewById<View>(R.id.rlRemove)
            ivPic = view.findViewById<ImageView>(R.id.ivPic)
            tvSchool = view.findViewById<TextView>(R.id.tvSchool)

            val displayWidth = DensityUtils.getDisplayWidth(view.context)
            val perWith = (displayWidth - DensityUtils.dip2px(view.context, 32 * 2f + 12 * 2)) / 3
            val lp = ConstraintLayout.LayoutParams(perWith, perWith)
            ivPic?.layoutParams = lp
            clAdd?.layoutParams = lp
        }
    }
}