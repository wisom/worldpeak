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
import com.worldpeak.chnsmilead.oa.model.SchoolItem
import com.worldpeak.chnsmilead.util.ReviewStatusUtil


/**
 * 组织架构-学校
 */
class ContactOrgSchoolListViewBinder(val callback: (SchoolItem) -> Unit) : ItemViewBinder<SchoolItem, ContactOrgSchoolListViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_contact_org_unit_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: SchoolItem) {
        holder.tvOrg?.text = entity.name
        holder.itemView?.setOnClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var tvHead: TextView? = null
        var tvOrg: TextView? = null

        init {
            tvHead = view.findViewById<TextView>(R.id.tvHead)
            tvOrg = view.findViewById<TextView>(R.id.tvOrg)
        }
    }
}