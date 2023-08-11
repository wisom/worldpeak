package com.worldpeak.chnsmilead.home.activity

import android.text.Html
import android.view.View
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityAreaDynamicDetailBinding
import com.worldpeak.chnsmilead.home.viewmodel.AreaDynamicDetailViewModel
import com.worldpeak.chnsmilead.util.DensityUtils
import com.worldpeak.chnsmilead.util.StatusBarUtil

/**
 *  区域教育动态详情
 */
class AreaDynamicDetailActivity : BaseVmVBActivity<AreaDynamicDetailViewModel, ActivityAreaDynamicDetailBinding>() {

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_TITLE = "EXTRA_TITLE"
    }

    private val mId by lazy {
        intent.getStringExtra(EXTRA_ID)
    }

    private val mTitle by lazy {
        intent.getStringExtra(EXTRA_TITLE)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_area_dynamic_detail
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun initView() {
        super.initView()
        mTitle?.let { mBinding.titleBar.setTitle(it) }
        mBinding.titleBar.showRightIcon(R.drawable.ic_share_black, DensityUtils.dip2px(this, 20f))
        mBinding.titleBar.setRightIconClick {

        }
    }

    override fun loadData() {
        mViewModel.getAreaDynamicDetail(mId)
        mViewModel.result.observe(this) {
            mBinding.tvSubTitle.text = it.title
            mBinding.tvAnchor.text = it.createName
            mBinding.tvTime.text = it.publishTime
            mBinding.tvContent.text = Html.fromHtml(it.content)
        }
    }
}