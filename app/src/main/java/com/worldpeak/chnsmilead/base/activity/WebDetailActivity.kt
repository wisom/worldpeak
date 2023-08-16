package com.worldpeak.chnsmilead.base.activity

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import com.umeng.socialize.shareboard.SnsPlatform
import com.umeng.socialize.utils.ShareBoardlistener
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityWebDetailBinding
import com.worldpeak.chnsmilead.util.StatusBarUtil


class WebDetailActivity : BaseVmVBActivity<BaseViewModel, ActivityWebDetailBinding>() {
    private val url by lazy {
        intent.getStringExtra(EXTRA_URL)
    }

    private val title by lazy {
        intent.getStringExtra(EXTRA_TITLE)
    }

    private var description:String? = ""

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
        mBinding.webview.webChromeClient = object :WebChromeClient(){
            override fun onReceivedTitle(view: WebView?, title: String?) {
                description = title
                ToastUtils.showLong(description)
            }
        };

        title?.let { mBinding.titleBar.setTitle(it) }
        url?.let { mBinding.webview.loadUrl(it) }
        mBinding.titleBar.setRightIconClick{
//            startActivity(SystemUtils.getShareTextIntent(url))

         ShareAction(this).setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
             .addButton("复制链接", "复制链接", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
             .setShareboardclickCallback(shareBoardlistener).open()

        }
    }

    override fun loadData() {
    }

    private var shareListener:UMShareListener? = object :UMShareListener{
        override fun onStart(p0: SHARE_MEDIA?) {
        }

        override fun onResult(p0: SHARE_MEDIA?) {

        }

        override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
        }

        override fun onCancel(p0: SHARE_MEDIA?) {
        }

    }

    var shareBoardlistener:ShareBoardlistener? = object :ShareBoardlistener{
        override fun onclick(snsPlatform: SnsPlatform?, share_media: SHARE_MEDIA?) {

           if (snsPlatform?.mShowWord == "复制链接") {
               val cm = this@WebDetailActivity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
               // 将文本内容放到系统剪贴板里。
               cm.text = url
                Toast.makeText(this@WebDetailActivity, "复制链接按钮", Toast.LENGTH_LONG).show()
            } else {
               val umImage = UMImage(this@WebDetailActivity, R.mipmap.ic_launcher)
               val umWeb = UMWeb(url)
               umWeb.title = title
               umWeb.description = description
               umWeb.setThumb(umImage)

               ShareAction(this@WebDetailActivity).withMedia(umWeb)
                    .setPlatform(share_media)
                    .setCallback(shareListener)
                    .share()
            }
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        shareBoardlistener = null
        shareListener = null
    }

}