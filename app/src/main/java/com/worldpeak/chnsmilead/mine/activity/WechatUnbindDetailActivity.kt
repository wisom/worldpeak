package com.worldpeak.chnsmilead.mine.activity

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.base.manager.AccountManager
import com.worldpeak.chnsmilead.databinding.ActivityWechatUnbindDetailBinding
import com.worldpeak.chnsmilead.login.model.WxUserInfo
import com.worldpeak.chnsmilead.mine.dialog.ListSelectDialog
import com.worldpeak.chnsmilead.mine.model.ListItemInfo
import com.worldpeak.chnsmilead.mine.model.WechatUnbindInfo
import com.worldpeak.chnsmilead.mine.viewmodel.WxUnbindViewModel
import com.worldpeak.chnsmilead.util.StatusBarUtil

/**
 * 微信解绑
 */
class WechatUnbindDetailActivity : BaseVmVBActivity<WxUnbindViewModel, ActivityWechatUnbindDetailBinding>() {

    companion object {
        const val EXTRA_INFO = "EXTRA_INFO"
    }

    private val mInfo by lazy {
        intent?.getParcelableExtra(EXTRA_INFO) as? WxUserInfo
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_wechat_unbind_detail
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        mBinding.titleBar.setRightIconClick {
            val list = mutableListOf<ListItemInfo>().apply {
                add(ListItemInfo(content = "解绑微信", click = View.OnClickListener {
                    mViewModel.wxUnbind(AccountManager.getUser().account?:"",mInfo?.openid?:"",0)
                }))
            }
            ListSelectDialog(this@WechatUnbindDetailActivity, list, true).show()
        }
        Glide.with(this).load(mInfo?.headimgurl).into(mBinding.ivAvatar)
        mBinding.tvName.setText("绑定微信：${mInfo?.nickname}")
    }

    override fun loadData() {
    }
}