package com.worldpeak.chnsmilead.mine.activity

import android.graphics.Color
import com.worldpeak.chnsmilead.MyApp
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityFeedbackBinding
import com.worldpeak.chnsmilead.databinding.ActivitySettingBinding
import com.worldpeak.chnsmilead.util.PkgUtil
import com.worldpeak.chnsmilead.util.StatusBarUtil

class FeedbackActivity : BaseVmVBActivity<BaseViewModel, ActivityFeedbackBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_feedback
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