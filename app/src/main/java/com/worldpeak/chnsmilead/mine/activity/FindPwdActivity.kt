package com.worldpeak.chnsmilead.mine.activity

import android.graphics.Color
import android.os.CountDownTimer
import com.blankj.utilcode.util.ToastUtils
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityFindPwdBinding
import com.worldpeak.chnsmilead.mine.viewmodel.FindPwdViewModel
import com.worldpeak.chnsmilead.util.PatternUtil
import com.worldpeak.chnsmilead.util.StatusBarUtil
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener
import java.util.*

/**
 * 找回密码
 */
class FindPwdActivity : BaseVmVBActivity<FindPwdViewModel, ActivityFindPwdBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_find_pwd
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    private var mTimer: CountDownTimer? = null

    private fun startTime() {
        mTimer?.cancel()
        mTimer = object : CountDownTimer(60 * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000 + 1
                mBinding.tvVerify.text = second.toString() + "s"
                mBinding.tvVerify.setTextColor(Color.parseColor("#FF0000"))
            }

            override fun onFinish() {
                mBinding.tvVerify.text = "获取验证码"
                mBinding.tvVerify.setTextColor(Color.parseColor("#249EFF"))
                mBinding.tvVerify.isEnabled = true
            }
        }
        mTimer?.start()
        mBinding.tvVerify.isEnabled = false
    }

    private fun stopTime() {
        mTimer?.cancel()
        mTimer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTime()
    }

    override fun initView() {
        super.initView()
        mBinding.tvVerify.setPreventDoubleClickListener {
            val phone = mBinding.etPhone.text.toString().trim()
            if (phone.isNullOrEmpty()) {
                ToastUtils.showShort("手机号码不能为空")
                return@setPreventDoubleClickListener
            }
            startTime()
            mViewModel.sendVerify(phone)
        }
        mBinding.tvSubmit.setPreventDoubleClickListener {
            val phone = mBinding.etPhone.text.toString().trim()
            val verifyCode = mBinding.etVerify.text.toString().trim()
            val newPwd = mBinding.etNew.text.toString().trim()
            val confirmPwd = mBinding.etConfirm.text.toString().trim()
            if (phone.isNullOrEmpty()) {
                ToastUtils.showShort("手机号码不能为空")
                return@setPreventDoubleClickListener
            }
            if (verifyCode.isNullOrEmpty()) {
                ToastUtils.showShort("验证码不能为空")
                return@setPreventDoubleClickListener
            }
            if (newPwd.isNullOrEmpty()) {
                ToastUtils.showShort("新密码不能为空")
                return@setPreventDoubleClickListener
            }
            if (confirmPwd.isNullOrEmpty()) {
                ToastUtils.showShort("确认密码不能为空")
                return@setPreventDoubleClickListener
            }
            if (!newPwd.equals(confirmPwd)) {
                ToastUtils.showShort("输入的新密码不一致")
                return@setPreventDoubleClickListener
            }
//            if (!PatternUtil.checkPwd(newPwd)) {
//                ToastUtils.showShort("密码必须是8-16位英文字母、数字、字符组合（不能是纯数字）");
//                return@setPreventDoubleClickListener
//            }

            mViewModel.findPwd(phone, verifyCode, newPwd)
        }
    }

    override fun loadData() {
    }
}