package com.worldpeak.chnsmilead.mine.activity

import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityPrivacyPolicyBinding
import com.worldpeak.chnsmilead.util.StatusBarUtil

/**
 * 隐私政策
 */
class PrivacyPolicyActivity : BaseVmVBActivity<BaseViewModel, ActivityPrivacyPolicyBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_privacy_policy
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun initView() {
        super.initView()
    }

    override fun loadData() {
    }
}