package com.worldpeak.chnsmilead.activity

import android.content.Intent
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.constant.Constants
import com.worldpeak.chnsmilead.databinding.ActivitySplashBinding
import com.worldpeak.chnsmilead.home.viewmodel.MainViewModel
import com.worldpeak.chnsmilead.main.LoginActivity
import com.worldpeak.chnsmilead.main.ProtocolActivity
import com.worldpeak.chnsmilead.util.SPUtils


class SplashActivity : BaseVmVBActivity<MainViewModel, ActivitySplashBinding>() {

    companion object {
        const val REQUEST_CODE = 0x112
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        val isFirstShow = SPUtils.getInstance().getBoolean(Constants.KEY_FIRST_SHOW_SPLASH, true)
        if (isFirstShow) {
            startActivityForResult(Intent(this@SplashActivity, ProtocolActivity::class.java), REQUEST_CODE)
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    override fun loadData() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                SPUtils.getInstance().putBoolean(Constants.KEY_FIRST_SHOW_SPLASH, false)
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                finish()
            }
        }
    }
}