package com.worldpeak.chnsmilead.oa.adapter.file

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.home.model.CommonAccessory
import com.worldpeak.chnsmilead.oa.adapter.notice.NoticeDeliveryListFileViewBinder
import com.worldpeak.chnsmilead.oa.model.FileDeliveryItem


/**
 * 文件下发
 */
class FileDeliveryListViewBinder(var context: Context?, val callback: (FileDeliveryItem) -> Unit) : ItemViewBinder<FileDeliveryItem, FileDeliveryListViewBinder.VH>() {

    private val mDataList: MutableList<CommonAccessory> = mutableListOf()
    private val mAdapter = MultiTypeAdapter().apply {
        register(CommonAccessory::class.java, NoticeDeliveryListFileViewBinder(context) {
        })
        items = mDataList
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_notice_delivery_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: FileDeliveryItem) {
        holder.tvTag?.text = getTagText(entity.grade)
        holder.tvTag?.setBackgroundResource(getTagBg(entity.grade))
        holder.tvTitle?.text = "         " + entity.title
        holder.tvContent?.text = entity.content
        holder.tvTime?.text = "发布时间：" + entity.createTime
        holder.tvStatus?.setBackgroundResource(getStatusBg(entity.status))
        holder.tvStatus?.text = getStatusText(entity.status)
        mDataList.clear()
        entity.papersDowmAccessoryList?.let { mDataList.addAll(it) }
        holder.rvFile?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        holder.rvFile?.adapter = mAdapter
        holder.itemView.setOnClickListener {
            callback.invoke(entity)
        }
    }

    /**
     * status:状态（0：未发送、1：已发送、3：已删除）
     */
    private fun getStatusText(status: Int?): String {
        return when (status) {
            1 -> {
                "通知已发出"
            }
            else -> {
                "通知未发出"
            }
        }
    }

    /**
     * status:状态（0：未发送、1：已发送、3：已删除）
     */
    private fun getStatusBg(status: Int?): Int {
        return when (status) {
            1 -> {
                R.drawable.shape_bg_green_radius_2dp
            }
            else -> {
                R.drawable.shape_bg_blue_radius_2dp
            }
        }
    }

    /**
     * grade:重要程度（1普通、2重要、3紧急）
     */
    private fun getTagText(grade: Int?): String {
        return when (grade) {
            2 -> {
                "重要"
            }
            3 -> {
                "紧急"
            }
            else -> {
                "普通"
            }
        }
    }

    /**
     * grade:重要程度（1普通、2重要、3紧急）
     */
    private fun getTagBg(grade: Int?): Int {
        return when (grade) {
            2 -> {
                R.drawable.shape_bg_orange_radius_8dp_lt_other
            }
            3 -> {
                R.drawable.shape_bg_red_radius_8dp_lt_other
            }
            else -> {
                R.drawable.shape_bg_blue_radius_8dp_lt_other
            }
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var tvTag: TextView? = null
        var tvTitle: TextView? = null
        var tvContent: TextView? = null
        var rvFile: RecyclerView? = null
        var tvTime: TextView? = null
        var tvStatus: TextView? = null

        init {
            tvTag = view.findViewById<TextView>(R.id.tvTag)
            tvTitle = view.findViewById<TextView>(R.id.tvTitle)
            tvContent = view.findViewById<TextView>(R.id.tvContent)
            rvFile = view.findViewById<RecyclerView>(R.id.rvFile)
            tvTime = view.findViewById<TextView>(R.id.tvTime)
            tvStatus = view.findViewById<TextView>(R.id.tvStatus)
        }
    }
}