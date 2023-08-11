package com.worldpeak.chnsmilead.mine.activity

import com.tencent.bugly.crashreport.CrashReport
import com.worldpeak.chnsmilead.MyApp
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.base.manager.AccountManager
import com.worldpeak.chnsmilead.databinding.ActivitySettingBinding
import com.worldpeak.chnsmilead.home.dialog.CommonConfirmDialog
import com.worldpeak.chnsmilead.util.PkgUtil
import com.worldpeak.chnsmilead.util.StatusBarUtil
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener

class SettingActivity : BaseVmVBActivity<BaseViewModel, ActivitySettingBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun initView() {
        super.initView()
        mBinding.switchBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
            } else {
            }
        }
        mBinding.tvVersion.text = "当前版本 " + PkgUtil.getVerName(MyApp.getContext())

        val user = AccountManager.getUser()
        mBinding.tvPhone.text = user.account

        mBinding.clearCache.setPreventDoubleClickListener {
            val dialog = CommonConfirmDialog(this, "确认清除缓存吗？", "取消", "确定", {}, {
            })
            dialog.show()
        }
    }

    override fun loadData() {
    }
}