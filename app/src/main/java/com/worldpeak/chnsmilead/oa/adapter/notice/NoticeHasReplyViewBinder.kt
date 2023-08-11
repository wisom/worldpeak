package com.worldpeak.chnsmilead.oa.adapter.notice

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.oa.model.DowmNoticeAccessory
import com.worldpeak.chnsmilead.oa.model.NoticeDeliveryReplyItem
import com.worldpeak.chnsmilead.util.WebUtil
import com.worldpeak.chnsmilead.util.hide
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener
import com.worldpeak.chnsmilead.util.show


/**
 * 通知详情已回复
 */
class NoticeHasReplyViewBinder(var context: Context?, val callback: (NoticeDeliveryReplyItem) -> Unit) : ItemViewBinder<NoticeDeliveryReplyItem, NoticeHasReplyViewBinder.VH>() {

    private val mFileList: MutableList<DowmNoticeAccessory> = mutableListOf()
    private val mFileAdapter = MultiTypeAdapter().apply {
        register(DowmNoticeAccessory::class.java, NoticeDeliveryListFileLongViewBinder(context) {
            WebUtil.openBrowser( it.fileUrl)
        })
        items = mFileList
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        return VH(
                inflater.inflate(
                        R.layout.layout_notice_has_reply_item,
                        parent,
                        false
                )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, entity: NoticeDeliveryReplyItem) {
        holder.tvSimple?.text = if (entity.noticeUserName.isNullOrEmpty()) "" else entity.noticeUserName?.substring(0, 1)
        holder.tvName?.text = entity.noticeUserName
        holder.tvGroup?.text = entity.schoolName
        holder.tvTime?.text = entity.replyDate
        holder.tvContent?.text = entity.replyRemark
        holder.rvAttach?.hide()
        if (!entity.dowmNoticeAccessoryList.isNullOrEmpty()) {
            mFileList.clear()
            mFileList.addAll(entity.dowmNoticeAccessoryList)
            holder.rvAttach?.show()
            holder.rvAttach?.layoutManager = LinearLayoutManager(holder.itemView.context)
            holder.rvAttach?.adapter = mFileAdapter
        }
        holder.itemView.setPreventDoubleClickListener {
            callback.invoke(entity)
        }
    }

    class VH(val view: View) : RecyclerView.ViewHolder(view) {
        var ivHead: ImageView? = null
        var tvSimple: TextView? = null
        var tvName: TextView? = null
        var tvGroup: TextView? = null
        var tvTime: TextView? = null
        var tvContent: TextView? = null
        var rvAttach: RecyclerView? = null

        init {
            ivHead = view.findViewById<ImageView>(R.id.ivHead)
            tvSimple = view.findViewById<TextView>(R.id.tvSimple)
            tvName = view.findViewById<TextView>(R.id.tvName)
            tvGroup = view.findViewById<TextView>(R.id.tvGroup)
            tvTime = view.findViewById<TextView>(R.id.tvTime)
            tvContent = view.findViewById<TextView>(R.id.tvContent)
            rvAttach = view.findViewById<RecyclerView>(R.id.rvAttach)
        }
    }
}