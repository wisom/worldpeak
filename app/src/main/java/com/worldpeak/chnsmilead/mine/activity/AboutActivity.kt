package com.worldpeak.chnsmilead.mine.activity

import com.bumptech.glide.Glide
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityAboutBinding
import com.worldpeak.chnsmilead.databinding.ActivityPrivacyPolicyBinding
import com.worldpeak.chnsmilead.util.StatusBarUtil

/**
 * 关于中国微校
 */
class AboutActivity : BaseVmVBActivity<BaseViewModel, ActivityAboutBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun initView() {
        super.initView()
        Glide.with(this).load(R.drawable.about_logo).into(mBinding.ivTop)
    }

    override fun loadData() {
    }
}