package com.worldpeak.chnsmilead.oa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.drakeet.multitype.ItemViewBinder
import com.tencent.imsdk.v2.V2TIMGroupInfo
import com.worldpeak.chnsmilead.R


/**
 * 群组列表
 */
class GroupListAdapter(val callback: (V2TIMGroupInfo) -> Unit) :
    ItemViewBinder<V2TIMGroupInfo, GroupListAdapter.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
            inflater.inflate(
                R.layout.adapter_im_group_item,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: V2TIMGroupInfo) {
        holder.tvGroup?.text = entity.groupName
        holder.tvGroupId?.text = entity.groupID
        holder.ivGroupImg?.load(entity.faceUrl) {
            error(R.mipmap.ic_launcher)
            placeholder(R.mipmap.ic_launcher)
        }
        holder.itemView.setOnClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var tvGroup: TextView? = null
        var tvGroupId: TextView? = null
        var ivGroupImg: ImageView? = null

        init {
            tvGroup = view.findViewById<TextView>(R.id.tvGroup)
            ivGroupImg = view.findViewById<ImageView>(R.id.ivGroupImg)
            tvGroupId = view.findViewById<TextView>(R.id.tvGroupId)
        }
    }
}