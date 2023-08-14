package com.worldpeak.chnsmilead.oa.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.drakeet.multitype.MultiTypeAdapter
import com.tencent.imsdk.v2.V2TIMGroupInfo
import com.tencent.qcloud.tuicore.TUIConstants
import com.tencent.qcloud.tuikit.tuichat.bean.ChatInfo
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIGroupChatActivity
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityGroupListBinding
import com.worldpeak.chnsmilead.oa.activity.contact.OaContactOrgActivity
import com.worldpeak.chnsmilead.oa.activity.contact.OaContactOrgUnitUserActivity
import com.worldpeak.chnsmilead.oa.adapter.GroupListAdapter
import com.worldpeak.chnsmilead.oa.viewmodel.GroupListVM
import com.worldpeak.chnsmilead.util.SelectPersonUtil

class GroupListActivity : BaseVmVBActivity<GroupListVM, ActivityGroupListBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_group_list
    }

    private val mAdapter = MultiTypeAdapter().apply {
        register(
            V2TIMGroupInfo::class.java,
            GroupListAdapter {
                Log.e(
                    "tag",
                    "it groupId = ${it.groupID} |name = ${it.groupName} |type= ${it.groupType}"
                )
                startActivity(
                    Intent(
                        this@GroupListActivity,
                        TUIGroupChatActivity::class.java
                    ).apply {
                        putExtras(Bundle().apply {
                            putExtra(TUIConstants.TUIChat.CHAT_TYPE, ChatInfo.TYPE_GROUP)
                            putExtra(TUIConstants.TUIChat.CHAT_ID, it.groupID)
                            putExtra(TUIConstants.TUIChat.CHAT_NAME, "")
                            putExtra(TUIConstants.TUIChat.DRAFT_TEXT, "")
                            putExtra(TUIConstants.TUIChat.DRAFT_TIME, 0)
                            putExtra(TUIConstants.TUIChat.IS_TOP_CHAT, false)
                            putExtra(TUIConstants.TUIChat.FACE_URL, it.faceUrl)
                            putExtra(TUIConstants.TUIChat.GROUP_NAME, it.groupName)
                            putExtra(TUIConstants.TUIChat.GROUP_TYPE, it.groupType)
                            putExtra(TUIConstants.TUIChat.JOIN_TYPE, "")
                            putExtra(TUIConstants.TUIChat.MEMBER_COUNT, it.memberCount)
                            putExtra(TUIConstants.TUIChat.RECEIVE_OPTION, it.recvOpt)
                            putExtra(TUIConstants.TUIChat.NOTICE, it.notification)
                            putExtra(TUIConstants.TUIChat.OWNER, it.owner)
//                            putExtra(TUIConstants.TUIChat.MEMBER_DETAILS, "")
                        })
                    })
            }
        )
    }

    override fun initView() {
        super.initView()
        val menu = LayoutInflater.from(this).inflate(R.layout.layout_add, null, false)
        menu.setOnClickListener {
            OaContactOrgActivity.startActivity(this, true)
        }
        mBinding.titleBar.addRightView(menu)

        mBinding.rcvGroupList.adapter = mAdapter

        mViewModel.groupList.observe(this) {
            if (it != null && it.isNotEmpty()) {
                mAdapter.items = it
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun loadData() {
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getGroupList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OaContactOrgUnitUserActivity.EXTRA_REQ_CODE && resultCode == RESULT_OK) {
            val name = if (SelectPersonUtil.getUserList().size > 2) {
                SelectPersonUtil.getUserList().subList(0, 2).map { it.name }
                    .joinToString("、")
            } else {
                SelectPersonUtil.getUserList().map { it.name }
                    .joinToString("、")
            }
            mViewModel.createGroup("${name}等")
        }
    }
}