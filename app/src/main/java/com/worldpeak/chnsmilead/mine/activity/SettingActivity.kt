package com.worldpeak.chnsmilead.mine.activity

import android.widget.CompoundButton
import com.tencent.bugly.crashreport.CrashReport
import com.worldpeak.chnsmilead.MyApp
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.base.manager.AccountManager
import com.worldpeak.chnsmilead.databinding.ActivitySettingBinding
import com.worldpeak.chnsmilead.home.dialog.CommonConfirmDialog
import com.worldpeak.chnsmilead.util.*

class SettingActivity : BaseVmVBActivity<BaseViewModel, ActivitySettingBinding>() {

    var buttonListener: CompoundButton.OnCheckedChangeListener? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun onResume() {
        mBinding.switchBtn?.apply {
            isChecked = NotificationUtil.isNotifyEnabled(context)
            createButtonListener()
        }

        super.onResume()
        StatusBarUtil.setLightMode(this)

    }

    override fun initView() {
        super.initView()
        mBinding.switchBtn?.apply {
            isChecked = NotificationUtil.isNotifyEnabled(context)
            createButtonListener()
        }
        mBinding.tvVersion.text = "当前版本 " + PkgUtil.getVerName(MyApp.getContext())

        val user = AccountManager.getUser()
        mBinding.tvPhone.text = user.account

        mBinding.clearCache.setPreventDoubleClickListener {
            val dialog = CommonConfirmDialog(this, "确认清除缓存吗？", "取消", "确定", {}, {
                CacheClearUtil.clearCache(context = this)
                mBinding.tvClearSize.text = CacheClearUtil.getCacheSize(context = this)
            })
            dialog.show()
        }
        mBinding.tvClearSize.text = CacheClearUtil.getCacheSize(context = this)
    }

    override fun loadData() {
    }

    fun createButtonListener() {
        if (buttonListener == null) {
            buttonListener = object :CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    if (buttonListener != null) {
                        NotificationUtil.startSettingNotify(this@SettingActivity)
                    }
                }
            }

            mBinding.switchBtn?.apply {
                setOnCheckedChangeListener(buttonListener)
            }
        }

    }

    override fun onPause() {
        buttonListener = null
        super.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()
        buttonListener = null
    }

}