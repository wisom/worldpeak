package com.worldpeak.chnsmilead.main

import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.worldpeak.chnsmilead.MyApp
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.activity.MainActivity
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.base.UseEventBus
import com.worldpeak.chnsmilead.constant.Constants
import com.worldpeak.chnsmilead.databinding.ActivityLoginBinding
import com.worldpeak.chnsmilead.event.GetWxCodeEvent
import com.worldpeak.chnsmilead.login.WxBindActivity
import com.worldpeak.chnsmilead.main.viewmodel.LoginViewModel
import com.worldpeak.chnsmilead.mine.activity.FindPwdActivity
import com.worldpeak.chnsmilead.util.CSClickableSpan
import com.worldpeak.chnsmilead.util.ConfigurationManager
import com.worldpeak.chnsmilead.util.SPUtils
import com.worldpeak.chnsmilead.util.StatusBarUtil
import com.worldpeak.chnsmilead.util.WXUtil
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@UseEventBus
class LoginActivity : BaseVmVBActivity<LoginViewModel, ActivityLoginBinding>() {

    private val descStr = "我已经阅读并同意中国微校《用户隐私政策》"
    private val descLightStr = "《用户隐私政策》"
    private val spUtils: SPUtils = SPUtils.getInstance()

    companion object {
        const val REQUEST_CODE = 0x111
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    private var isHidePwd = true
    override fun initView() {
        super.initView()
        val lastAccount = spUtils.getString(Constants.KEY_LAST_ACCOUNT, "")
        val lastPwd = spUtils.getString(Constants.KEY_LAST_PWD, "")
        val rememberPwd = spUtils.getBoolean(Constants.KEY_REMEMBER_PWD, false)
        if (!lastAccount.isNullOrEmpty()) {
            mBinding.etAccount.setText(lastAccount.toString())
        }
        if (rememberPwd) {
            if (!lastPwd.isNullOrEmpty()) {
                mBinding.etPwd.setText(lastPwd.toString())
            }
            mBinding.cbRemind.isChecked = true
        } else {
            mBinding.cbRemind.isChecked = false
        }
        mBinding.tvAccountLogin.setPreventDoubleClickListener {
            val account = mBinding.etAccount.text.toString().trim()
            val pwd = mBinding.etPwd.text.toString().trim()
            val remindPwd = mBinding.cbRemind.isChecked
            val agreeProtocol = mBinding.cbAgree.isChecked
            if (account.isNullOrEmpty()) {
                ToastUtils.showShort("请输入账号")
                return@setPreventDoubleClickListener
            }
            if (pwd.isNullOrEmpty()) {
                ToastUtils.showShort("请输入密码")
                return@setPreventDoubleClickListener
            }
            if (!agreeProtocol) {
                ToastUtils.showShort("请同意用户隐私政策")
                return@setPreventDoubleClickListener
            }
            mViewModel.login(account, pwd, "5", remindPwd)
//            mViewModel.login("19131013102", "xz*123456", "5", remindPwd)
        }
        mViewModel._getInfoSuccess.observe(this) {
            if (it) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
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
        mBinding.tvAgree.movementMethod = LinkMovementMethod.getInstance()
        mBinding.tvAgree.text = SpannableString(descStr).apply {
            if (descStr.contains(descLightStr) && descStr.indexOf(descLightStr) != -1) {
                setSpan(
                    CSClickableSpan(Color.parseColor("#04B0EF"), View.OnClickListener {
                        startActivityForResult(
                            Intent(this@LoginActivity, ProtocolActivity::class.java),
                            REQUEST_CODE
                        )
                    }),
                    descStr.indexOf(descLightStr),
                    descStr.indexOf(descLightStr) + descLightStr.length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        }
        mBinding.ivWechatLogin.setPreventDoubleClickListener {
            val agreeProtocol = mBinding.cbAgree.isChecked
            if (!agreeProtocol) {
                ToastUtils.showShort("请同意用户隐私政策")
                return@setPreventDoubleClickListener
            }
            ConfigurationManager.instance()
                .setString(Constants.PREF_KEY_URL, Constants.SERVER_URL_ORIGIN)
            val accessToken = spUtils.getString(Constants.PREF_KEY_WX_ACCESSTOKEN)
            if (TextUtils.isEmpty(accessToken)) {
                WXUtil.sendAuth()
            } else {
                val openId: String = spUtils.getString(Constants.PREF_KEY_WX_OPENID)
                mViewModel.verifyToken(accessToken, openId, 5)
            }
        }
        mViewModel.unBindInfo.observe(this, Observer {
            val intent = Intent(this, WxBindActivity::class.java)
            intent.putExtra(WxBindActivity.EXTRA_ACCESSTOKEN, it.first)
            intent.putExtra(WxBindActivity.EXTRA_OPENID, it.second)
            intent.putExtra(WxBindActivity.EXTRA_USERID, it.third)
            startActivity(intent);
        })
        mBinding.tvForget.setPreventDoubleClickListener {
            startActivity(Intent(this@LoginActivity, FindPwdActivity::class.java))
        }
        MyApp.strategy.deviceModel = android.os.Build.MODEL;
    }

    override fun loadData() {
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            mBinding.cbAgree.isChecked = true
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: GetWxCodeEvent) {
        if (!TextUtils.isEmpty(event.wxCode)) {
            mViewModel.getWxAccessToken(event.wxCode)
        }
    }
}
