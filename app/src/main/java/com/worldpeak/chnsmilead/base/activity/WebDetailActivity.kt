package com.worldpeak.chnsmilead.base.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
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

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        super.initView()
        mBinding.webview.settings.javaScriptEnabled = true;
        title?.let { mBinding.titleBar.setTitle(it) }
        url?.let { mBinding.webview.loadUrl(it) }
        mBinding.titleBar.setRightIconClick{
//            startActivity(SystemUtils.getShareTextIntent(url))
            val umImage = UMImage(this, R.mipmap.ic_launcher)
            val umWeb = UMWeb(url)
            umWeb.title = title
            umWeb.setThumb(umImage)

         ShareAction(this).withMedia(umWeb).setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
             .addButton("复制链接", "复制链接", url, url)
             .setCallback(shareListner).open()

        }
    }

    override fun loadData() {
    }

    private val shareListner = object :UMShareListener{
        override fun onStart(p0: SHARE_MEDIA?) {
        }

        override fun onResult(p0: SHARE_MEDIA?) {
        }

        override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
        }

        override fun onCancel(p0: SHARE_MEDIA?) {
        }

    }
}