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
import com.worldpeak.chnsmilead.oa.model.StudentLeaveDetail
import com.worldpeak.chnsmilead.oa.viewmodel.DocumentApproveInfo


/**
 * 学生缺勤
 */
class StudentAbsenceViewBinder(var context: Context?) : ItemViewBinder<StudentLeaveDetail, StudentAbsenceViewBinder.VH>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_absence_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: StudentLeaveDetail) {
        holder.tvName?.text = entity.userName
        holder.tvSchool?.text = entity.schoolName
        holder.tvClass?.text = entity.className
        holder.tvDate?.text = entity.leaveDate
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView? = null
        var tvSchool: TextView? = null
        var tvClass: TextView? = null
        var tvDate: TextView? = null

        init {
            tvName = view.findViewById<TextView>(R.id.tvName)
            tvSchool = view.findViewById<TextView>(R.id.tvSchool)
            tvClass = view.findViewById<TextView>(R.id.tvClass)
            tvDate = view.findViewById<TextView>(R.id.tvDate)
        }
    }
}