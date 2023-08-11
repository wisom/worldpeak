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
import com.worldpeak.chnsmilead.oa.model.ContactOrgItem
import com.worldpeak.chnsmilead.util.ReviewStatusUtil


/**
 * 组织架构
 */
class ContactOrgListViewBinder(val callback: (ContactOrgItem) -> Unit) : ItemViewBinder<ContactOrgItem, ContactOrgListViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_contact_org_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: ContactOrgItem) {
        holder.tvOrg?.text = entity.name
        holder.itemView?.setOnClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var tvOrg: TextView? = null

        init {
            tvOrg = view.findViewById<TextView>(R.id.tvOrg)
        }
    }
}