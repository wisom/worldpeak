package com.worldpeak.chnsmilead.oa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.oa.model.SchoolItem
import com.worldpeak.chnsmilead.oa.model.Person
import com.worldpeak.chnsmilead.util.show


/**
 * 组织架构-学校-用户
 */
class ContactOrgSchoolUserListViewBinder(val canEdit: Boolean, val school: SchoolItem?, val callback: (Pair<Int, Person>) -> Unit) : ItemViewBinder<Person, ContactOrgSchoolUserListViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_contact_org_unit_user_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: Person) {
        if (canEdit) {
            holder.ivSelect?.show()
            holder.ivSelect?.isActivated = entity.isSelect == true
        }
        holder.tvHead?.text = if (entity.name.isNullOrEmpty()) "" else entity.name.substring(0, 1)
        holder.tvName?.text = entity.name
        holder.tvUnit?.text = (school?.name ?: "") + "-" + (entity.department ?: "")
        holder.itemView.setOnClickListener {
            entity.isSelect = entity.isSelect != true
            callback.invoke(Pair(holder.position, entity))
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var ivSelect: ImageView? = null
        var tvHead: TextView? = null
        var tvName: TextView? = null
        var tvUnit: TextView? = null

        init {
            ivSelect = view.findViewById<ImageView>(R.id.ivSelect)
            tvHead = view.findViewById<TextView>(R.id.tvHead)
            tvName = view.findViewById<TextView>(R.id.tvName)
            tvUnit = view.findViewById<TextView>(R.id.tvUnit)
        }
    }
}