package com.worldpeak.chnsmilead.oa.activity

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import com.drakeet.multitype.MultiTypeAdapter
import com.google.gson.Gson
import com.tencent.bugly.proguard.bu
import com.tencent.imsdk.v2.V2TIMGroupInfo
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityGroupListBinding
import com.worldpeak.chnsmilead.oa.activity.contact.OaContactActivity
import com.worldpeak.chnsmilead.oa.activity.contact.OaContactOrgActivity
import com.worldpeak.chnsmilead.oa.activity.contact.OaContactOrgUnitUserActivity
import com.worldpeak.chnsmilead.oa.activity.notice.NoticeDeliveryAddActivity
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
            GroupListAdapter { Log.e("tag", "it = ${Gson().toJson(it)}") }
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