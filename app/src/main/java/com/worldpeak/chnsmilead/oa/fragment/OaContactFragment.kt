package com.worldpeak.chnsmilead.oa.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.drakeet.multitype.MultiTypeAdapter
import com.tencent.qcloud.tuicore.TUIConstants
import com.tencent.qcloud.tuikit.tuichat.bean.ChatInfo
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIC2CChatActivity
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIGroupChatActivity
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBFragment
import com.worldpeak.chnsmilead.databinding.FragmentOaContactBinding
import com.worldpeak.chnsmilead.oa.activity.GroupListActivity
import com.worldpeak.chnsmilead.oa.activity.contact.OaContactOrgActivity
import com.worldpeak.chnsmilead.oa.adapter.ContactOrgSchoolUserListViewBinder
import com.worldpeak.chnsmilead.oa.model.Person
import com.worldpeak.chnsmilead.oa.viewmodel.OaContactViewModel
import com.worldpeak.chnsmilead.util.SelectPersonUtil
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener

class OaContactFragment : BaseVmVBFragment<OaContactViewModel, FragmentOaContactBinding>(),
    View.OnClickListener {
    val mContactAdapter = MultiTypeAdapter().apply {
        register(Person::class.java, ContactOrgSchoolUserListViewBinder(false, null) {
            startActivity(
                Intent(
                    requireContext(),
                    TUIC2CChatActivity::class.java
                ).apply {
                    putExtras(Bundle().apply {
                        putExtra(TUIConstants.TUIChat.CHAT_TYPE, ChatInfo.TYPE_C2C)
                        putExtra(TUIConstants.TUIChat.CHAT_ID, it.second.id)
                        putExtra(TUIConstants.TUIChat.CHAT_NAME, it.second.avatarImg)
                        putExtra(TUIConstants.TUIChat.DRAFT_TEXT, "")
                        putExtra(TUIConstants.TUIChat.DRAFT_TIME, 0)
                        putExtra(TUIConstants.TUIChat.IS_TOP_CHAT, false)
                        putExtra(TUIConstants.TUIChat.FACE_URL, it.second.avatarImg)
                    })
                })
        })
    }

    companion object {
        const val EXTRA_CAN_EDIT = "EXTRA_CAN_EDIT"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_oa_contact
    }

    override fun initView() {
        super.initView()
        val canEdit = arguments?.getBoolean(EXTRA_CAN_EDIT) ?: false
        mBinding.llOrg.setPreventDoubleClickListener {
            activity?.let { it1 -> OaContactOrgActivity.startActivity(it1, canEdit) }
        }

        mBinding.llGroup.setOnClickListener(this)
    }

    private val mUserList: MutableList<Person> = mutableListOf()
    override fun loadData() {
        mViewModel.getContactList()

        mBinding.rvContact.adapter = mContactAdapter

        mViewModel.contactList.observe(this) {
            if (it != null && it.isNotEmpty()) {
                it.forEach { sub ->
                    sub.children.forEach { department ->
                        department.children?.forEach { user ->
                            user.children?.forEach { tempIt ->
                                tempIt.department = department.name
                                mUserList.add(tempIt)
                            }
                        }
                    }
                }
                mContactAdapter.items = mUserList
                mContactAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.llGroup -> startActivity(Intent(activity, GroupListActivity::class.java))
        }
    }
}