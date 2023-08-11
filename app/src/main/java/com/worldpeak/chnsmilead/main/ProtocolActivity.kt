package com.worldpeak.chnsmilead.main

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.View
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.base.activity.WebDetailActivity
import com.worldpeak.chnsmilead.constant.Constants
import com.worldpeak.chnsmilead.databinding.ActivityProtocolBinding
import com.worldpeak.chnsmilead.util.CSClickableSpan
import com.worldpeak.chnsmilead.util.StatusBarUtil
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener

/**
 * 隐私政策
 */
class ProtocolActivity : BaseVmVBActivity<BaseViewModel, ActivityProtocolBinding>() {

    private val descStr = "您在使用微校产品/服务前,请仔细阅读并充分理解《个人信息保护及隐私政策》、《用户协议》。当您点击同意,即表示您已经理解并同意该条款,该条款将构成对您具有法律约東力的文件。"
    private val descLight1Str = "个人信息保护及隐私政策》"
    private val descLight2Str = "《用户协议》"
    private val threeSDKStr = "未经您的单独同意,我们不会主动向任何第三方共享您的个人信息。当您使用一些功能服务时,我们可能会在获得您的明示同意后,从授权第三方处获取、共享或向其提供信息。您可阅读《第三方SDK说明》了解详细信息。"
    private val threeSDKLightStr = "《第三方SDK说明》"

    override fun getLayoutId(): Int {
        return R.layout.activity_protocol
    }

    override fun initView() {
        super.initView()
        mBinding.tvUnagree.setPreventDoubleClickListener {
            setResult(RESULT_CANCELED, intent)
            finish()
        }
        mBinding.tvAgree.setPreventDoubleClickListener {
            setResult(RESULT_OK, intent)
            finish()
        }

        // 必须设置 TextView 的 movementMethod 为 LinkMovementMethod，否则标记无法响应点击事件
        mBinding.tvDesc.movementMethod = LinkMovementMethod.getInstance()
        mBinding.tvDesc.text = SpannableString(descStr).apply {
            if (descStr.contains(descLight1Str) && descStr.indexOf(descLight1Str) != -1) {
                setSpan(CSClickableSpan(Color.parseColor("#04B0EF"), View.OnClickListener {
                    val url = Constants.SERVER_URL_ORIGIN + "app-api/app/school/page/platform/WX_USER_PRIVACY"
                    WebDetailActivity.startActivity(this@ProtocolActivity, "隐私政策", url)
                }), descStr.indexOf(descLight1Str), descStr.indexOf(descLight1Str) + descLight1Str.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (descStr.contains(descLight2Str) && descStr.indexOf(descLight2Str) != -1) {
                setSpan(CSClickableSpan(Color.parseColor("#04B0EF"), View.OnClickListener {
                    val url = "http://yun3mgr.csmiledu.com/wxUserAgreement"
                    WebDetailActivity.startActivity(this@ProtocolActivity, "用户协议", url)
                }), descStr.indexOf(descLight2Str), descStr.indexOf(descLight2Str) + descLight2Str.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        mBinding.tvRead.movementMethod = LinkMovementMethod.getInstance()
        mBinding.tvRead.text = SpannableString(threeSDKStr).apply {
            if (threeSDKStr.contains(threeSDKLightStr) && threeSDKStr.indexOf(threeSDKLightStr) != -1) {
                setSpan(CSClickableSpan(Color.parseColor("#04B0EF"), View.OnClickListener {
                    val url = "http://yun3mgr.csmiledu.com/wxAppSDKExplain"
                    WebDetailActivity.startActivity(this@ProtocolActivity, "第三方SDK说明", url)
                }), threeSDKStr.indexOf(threeSDKLightStr), threeSDKStr.indexOf(threeSDKLightStr) + threeSDKLightStr.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
    }

    override fun loadData() {
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }
}