package com.worldpeak.chnsmilead.contact.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.google.android.material.imageview.ShapeableImageView
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.contact.model.ContactItem


/**
 */
class ContactViewBinder(var context: Context?) : ItemViewBinder<ContactItem, ContactViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_contact_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: ContactItem) {
        holder.tvName?.text = entity.name
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var sivAvatar: ShapeableImageView? = null
        var tvName: TextView? = null
        var tvContent: TextView? = null
        var tvPot: TextView? = null
        var tvTime: TextView? = null

        init {
            sivAvatar = view.findViewById<ShapeableImageView>(R.id.sivAvatar)
            tvName = view.findViewById<TextView>(R.id.tvName)
            tvContent = view.findViewById<TextView>(R.id.tvContent)
            tvPot = view.findViewById<TextView>(R.id.tvPot)
            tvTime = view.findViewById<TextView>(R.id.tvTime)
        }
    }
}