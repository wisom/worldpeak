package com.worldpeak.chnsmilead.oa.activity.contact

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityOaContactBinding
import com.worldpeak.chnsmilead.oa.fragment.OaContactFragment
import com.worldpeak.chnsmilead.oa.viewmodel.OaContactViewModel
import com.worldpeak.chnsmilead.util.*

/**
 * oa系统通讯录详情
 */
class OaContactActivity : BaseVmVBActivity<BaseViewModel, ActivityOaContactBinding>() {

    companion object {
        const val EXTRA_CANEDIT = "EXTRA_CANEDIT"
        const val EXTRA_TITLE = "EXTRA_TITLE"

        fun startActivity(context: Activity, canEdit: Boolean, title: String) {
            val intent = Intent(context, OaContactActivity::class.java)
            intent.putExtra(EXTRA_CANEDIT, canEdit)
            intent.putExtra(EXTRA_TITLE, title)
            context.startActivityForResult(intent,OaContactOrgUnitUserActivity.EXTRA_REQ_CODE)
        }
    }

    private val mCanEdit by lazy {
        intent.getBooleanExtra(EXTRA_CANEDIT, false)
    }

    private val mTitle by lazy {
        intent.getStringExtra(EXTRA_TITLE)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_oa_contact
    }

    override fun initView() {
        super.initView()
        mTitle?.let { mBinding.titleBar.setTitle(it) }
        mBinding.titleBar.setBackClick {
            SelectPersonUtil.clearList()
            finish()
        }
        val contactFragment = OaContactFragment()
        contactFragment.arguments = Bundle().apply {
            putBoolean(OaContactFragment.EXTRA_CAN_EDIT, mCanEdit)
        }
        supportFragmentManager.beginTransaction().add(R.id.rlContainer, contactFragment).commit()
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