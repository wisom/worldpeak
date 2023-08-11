package com.worldpeak.chnsmilead.mine.activity

import android.content.Intent
import com.blankj.utilcode.util.ToastUtils
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityChangePwdBinding
import com.worldpeak.chnsmilead.mine.viewmodel.ChangePwdViewModel
import com.worldpeak.chnsmilead.util.StatusBarUtil
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener

/**
 * 修改密码
 */
class ChangePwdActivity : BaseVmVBActivity<ChangePwdViewModel, ActivityChangePwdBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_change_pwd
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun initView() {
        super.initView()
        val originPwd = mBinding.etOrigin.text.toString().trim()
        val newPwd = mBinding.etNew.text.toString().trim()
        val confirmPwd = mBinding.etConfirm.text.toString().trim()
        mBinding.tvSubmit.setPreventDoubleClickListener {
            if (originPwd.isNullOrEmpty()) {
                ToastUtils.showShort("原密码不能为空")
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

            mViewModel.changePwd(originPwd, newPwd)
        }
        mBinding.tvFindPwd.setPreventDoubleClickListener {
            startActivity(Intent(this@ChangePwdActivity, FindPwdActivity::class.java))
        }
    }

    override fun loadData() {
    }
}