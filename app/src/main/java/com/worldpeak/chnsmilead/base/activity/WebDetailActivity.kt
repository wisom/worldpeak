package com.worldpeak.chnsmilead.base.activity

import android.content.Context
import android.content.Intent
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityWebDetailBinding
import com.worldpeak.chnsmilead.util.StatusBarUtil
import com.worldpeak.chnsmilead.util.SystemUtils

class WebDetailActivity : BaseVmVBActivity<BaseViewModel, ActivityWebDetailBinding>() {

    private val url by lazy {
        intent.getStringExtra(EXTRA_URL)
    }

    private val title by lazy {
        intent.getStringExtra(EXTRA_TITLE)
    }

    companion object {
        const val EXTRA_URL = "EXTRA_URL"
        const val EXTRA_TITLE = "EXTRA_TITLE"

        @JvmStatic
        fun startActivity(context: Context?, title: String, url: String?) {
            if (context == null) return
            val intent = Intent(context, WebDetailActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_URL, url)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_web_detail
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun initView() {
        super.initView()
        title?.let { mBinding.titleBar.setTitle(it) }
        url?.let { mBinding.webview.loadUrl(it) }
        mBinding.titleBar.setRightIconClick{
            startActivity(SystemUtils.getShareTextIntent(url))
        }
    }

    override fun loadData() {
    }
}