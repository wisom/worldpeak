package com.worldpeak.chnsmilead.mine.activity

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.google.gson.Gson
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.base.manager.AccountManager
import com.worldpeak.chnsmilead.contact.adapter.ContactViewBinder
import com.worldpeak.chnsmilead.contact.model.ContactItem
import com.worldpeak.chnsmilead.databinding.ActivityWechatUnbindBinding
import com.worldpeak.chnsmilead.login.model.WxUserInfo
import com.worldpeak.chnsmilead.mine.adapter.WechatUnbindViewBinder
import com.worldpeak.chnsmilead.mine.model.WechatUnbindInfo
import com.worldpeak.chnsmilead.mine.viewmodel.WxUnbindViewModel
import com.worldpeak.chnsmilead.util.StatusBarUtil

/**
 * 微信解绑
 */
class WechatUnbindActivity : BaseVmVBActivity<WxUnbindViewModel, ActivityWechatUnbindBinding>() {

    private val mDataList: MutableList<WxUserInfo> = mutableListOf()
    private val mAdapter = MultiTypeAdapter().apply {
        register(WxUserInfo::class.java, WechatUnbindViewBinder(this@WechatUnbindActivity) {
            val intent = Intent(this@WechatUnbindActivity, WechatUnbindDetailActivity::class.java)
            intent.putExtra(WechatUnbindDetailActivity.EXTRA_INFO, it)
            startActivity(intent)
        })
        items = mDataList
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_wechat_unbind
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun initView() {
        super.initView()
        mBinding.rvUnbind.layoutManager = LinearLayoutManager(this)
        mBinding.rvUnbind.adapter = mAdapter
    }

    override fun loadData() {
        mViewModel.getWxUnbindList(AccountManager.getUser().account)
        mViewModel.userList.observe(this, Observer {
            mDataList.clear()
            mDataList.addAll(it)
            mAdapter.notifyDataSetChanged()
        })
    }
}