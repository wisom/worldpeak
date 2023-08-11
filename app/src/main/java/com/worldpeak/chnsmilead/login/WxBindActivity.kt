package com.worldpeak.chnsmilead.login

import android.content.Intent
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.activity.MainActivity
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityWxBindBinding
import com.worldpeak.chnsmilead.main.viewmodel.WxBindViewModel
import com.worldpeak.chnsmilead.util.SPUtils
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener

class WxBindActivity : BaseVmVBActivity<WxBindViewModel, ActivityWxBindBinding>() {

    private val spUtils: SPUtils = SPUtils.getInstance()

    override fun getLayoutId(): Int {
        return R.layout.activity_wx_bind
    }

    private var isHidePwd = false
    override fun initView() {
        mBinding.tvBinding.setOnClickListener(View.OnClickListener {
            val account: String = mBinding.etAccount.getText().toString()
            val password: String = mBinding.etPwd.getText().toString()
            KeyboardUtils.hideSoftInput(this@WxBindActivity)
            if (TextUtils.isEmpty(account)) {
                showToast(R.string.login_account_error)
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                showToast(R.string.login_password_error)
                return@OnClickListener
            }
            val intent: Intent = getIntent()
            val accessToken = intent.getStringExtra(EXTRA_ACCESSTOKEN) ?: ""
            val openId = intent.getStringExtra(EXTRA_OPENID) ?: ""
            val userIdentity: Int = intent.getIntExtra(EXTRA_USERID, 1)
            mViewModel.wxLoginReq(accessToken, openId, account, password, userIdentity)
        })
        mBinding.tvLookPwd.setPreventDoubleClickListener {
            if (isHidePwd) {
                mBinding.etPwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                isHidePwd = false
                mBinding.etPwd.setSelection(mBinding.etPwd.text.toString().length)
            } else {
                mBinding.etPwd.transformationMethod = PasswordTransformationMethod.getInstance();
                isHidePwd = true
                mBinding.etPwd.setSelection(mBinding.etPwd.text.toString().length)
            }
        }
        mBinding.tvCancel.setPreventDoubleClickListener {
            finish()
        }
        mViewModel._getInfoSuccess.observe(this) {
            if (it) {
                startActivity(Intent(this@WxBindActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun loadData() {
    }

    private fun showToast(message: Int) {
        ThreadUtils.runOnUiThread { ToastUtils.showShort(message) }
    }

    private fun showToast(message: String) {
        ThreadUtils.runOnUiThread { ToastUtils.showShort(message) }
    }

    companion object {
        var EXTRA_ACCESSTOKEN = "EXTRA_ACCESSTOKEN"
        var EXTRA_OPENID = "EXTRA_OPENID"
        var EXTRA_USERID = "EXTRA_USERID"
    }
}