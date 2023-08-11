package com.worldpeak.chnsmilead.oa.activity.contact

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityOaContactOrgBinding
import com.worldpeak.chnsmilead.oa.adapter.ContactOrgListViewBinder
import com.worldpeak.chnsmilead.oa.model.*
import com.worldpeak.chnsmilead.oa.viewmodel.OaContactViewModel
import com.worldpeak.chnsmilead.util.*

/**
 * oa通讯录组织架构详情
 */
class OaContactOrgActivity : BaseVmVBActivity<OaContactViewModel, ActivityOaContactOrgBinding>() {

    companion object {
        const val EXTRA_CANEDIT = "EXTRA_CANEDIT"

        fun startActivity(context: Activity,canEdit:Boolean) {
            val intent = Intent(context, OaContactOrgActivity::class.java)
            intent.putExtra(EXTRA_CANEDIT,canEdit)
            context.startActivityForResult(intent,OaContactOrgUnitUserActivity.EXTRA_REQ_CODE)
        }
    }

    private val mCanEdit by lazy {
        intent.getBooleanExtra(OaContactOrgUnitUserActivity.EXTRA_CANEDIT, false)
    }

    private val mContactList: MutableList<ContactOrgItem> = mutableListOf()
    private val mContactAdapter = MultiTypeAdapter().apply {
        register(ContactOrgItem::class.java, ContactOrgListViewBinder() {
            OaContactOrgUnitActivity.startActivity(this@OaContactOrgActivity, it, mCanEdit)
        })
        items = mContactList
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_oa_contact_org
    }

    override fun initView() {
        super.initView()
        mBinding.rvContact.layoutManager = LinearLayoutManager(this)
        mBinding.rvContact.adapter = mContactAdapter
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    @SuppressLint("SetTextI18n")
    override fun loadData() {
        mViewModel.getContactList()
        mViewModel.contactList.observe(this, Observer { contactList ->
            mContactList.clear()
            mContactList.addAll(contactList)
            mContactAdapter.notifyDataSetChanged()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OaContactOrgUnitUserActivity.EXTRA_REQ_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}