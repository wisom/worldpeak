package com.worldpeak.chnsmilead.mine

import android.content.Intent
import com.bumptech.glide.Glide
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBFragment
import com.worldpeak.chnsmilead.base.activity.WebDetailActivity
import com.worldpeak.chnsmilead.base.manager.AccountManager
import com.worldpeak.chnsmilead.constant.Constants
import com.worldpeak.chnsmilead.databinding.FragmentMineBinding
import com.worldpeak.chnsmilead.home.dialog.CommonConfirmDialog
import com.worldpeak.chnsmilead.main.LoginActivity
import com.worldpeak.chnsmilead.mine.activity.*
import com.worldpeak.chnsmilead.util.StatusBarUtil
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener

class MineFragment : BaseVmVBFragment<BaseViewModel, FragmentMineBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        super.initView()
        mBinding.ivSetting.setPreventDoubleClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
        }
        mBinding.llSetting.setPreventDoubleClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
        }
        mBinding.llChangePwd.setPreventDoubleClickListener {
            startActivity(Intent(activity, ChangePwdActivity::class.java))
        }
        mBinding.llFeedback.setPreventDoubleClickListener {
            startActivity(Intent(activity, FeedbackActivity::class.java))
        }
        mBinding.llPrivacy.setPreventDoubleClickListener {
//            startActivity(Intent(activity, PrivacyPolicyActivity::class.java))
            val url = Constants.SERVER_URL_ORIGIN + "app-api/app/school/page/platform/WX_USER_PRIVACY"
            WebDetailActivity.startActivity(activity, "隐私政策", url)
        }
        mBinding.llWechat.setPreventDoubleClickListener {
            startActivity(Intent(activity, WechatUnbindActivity::class.java))
        }
        mBinding.llAbout.setPreventDoubleClickListener {
            startActivity(Intent(activity, AboutActivity::class.java))
        }
        mBinding.llLogout.setPreventDoubleClickListener {
            val dialog = activity?.let { it1 ->
                CommonConfirmDialog(it1, "确认退出登录吗？", "取消", "确定", {}, {
                    AccountManager.onLogout()
                    startActivity(Intent(activity, LoginActivity::class.java))
//                    Router.open(Constants.SP_BASE_LOGIN, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }
            dialog?.show()
        }

        val user = AccountManager.getUser()
        mBinding.tvName.text = user.name
        mBinding.tvSimple.text = if (user.name.isNullOrEmpty()) "" else user.name.substring(0, 1)
        Glide.with(this).load(user.avatar).into(mBinding.sivAvatar)
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setDarkMode(activity)
    }

    override fun loadData() {
    }

}