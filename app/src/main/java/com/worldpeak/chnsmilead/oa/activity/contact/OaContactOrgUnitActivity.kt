package com.worldpeak.chnsmilead.oa.activity.contact

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityOaContactOrgUnitBinding
import com.worldpeak.chnsmilead.oa.adapter.*
import com.worldpeak.chnsmilead.oa.model.*
import com.worldpeak.chnsmilead.oa.viewmodel.OaContactViewModel
import com.worldpeak.chnsmilead.util.*

/**
 * oa通讯录组织架构单位详情
 */
class OaContactOrgUnitActivity : BaseVmVBActivity<OaContactViewModel, ActivityOaContactOrgUnitBinding>() {

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        const val EXTRA_CANEDIT = "EXTRA_CANEDIT"


        fun startActivity(context: Activity, data: ContactOrgItem, canEdit: Boolean) {
            val intent = Intent(context, OaContactOrgUnitActivity::class.java)
            intent.putExtra(EXTRA_DATA, data)
            intent.putExtra(EXTRA_CANEDIT, canEdit)
            context.startActivityForResult(intent,OaContactOrgUnitUserActivity.EXTRA_REQ_CODE)
        }
    }

    private val mUnit by lazy {
        intent.getParcelableExtra<ContactOrgItem>(EXTRA_DATA)
    }

    private val mCanEdit by lazy {
        intent.getBooleanExtra(OaContactOrgUnitUserActivity.EXTRA_CANEDIT, false)
    }

    private val mSchoolList: MutableList<SchoolItem> = mutableListOf()
    private val mContactAdapter = MultiTypeAdapter().apply {
        register(SchoolItem::class.java, ContactOrgSchoolListViewBinder() {
            OaContactOrgUnitUserActivity.startActivity(this@OaContactOrgUnitActivity, it, mCanEdit)
        })
        items = mSchoolList
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_oa_contact_org_unit
    }

    override fun initView() {
        super.initView()
        mBinding.titleBar.setTitle(mUnit?.name ?: "")
        mBinding.tvUnit.text = mUnit?.name
        mSchoolList.clear()
        mUnit?.children?.let { mSchoolList.addAll(it) }
        mBinding.rvContact.layoutManager = LinearLayoutManager(this)
        mBinding.rvContact.adapter = mContactAdapter
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    @SuppressLint("SetTextI18n")
    override fun loadData() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OaContactOrgUnitUserActivity.EXTRA_REQ_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}