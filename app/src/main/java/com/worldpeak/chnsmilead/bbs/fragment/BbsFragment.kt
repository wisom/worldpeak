package com.worldpeak.chnsmilead.bbs.fragment

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBFragment
import com.worldpeak.chnsmilead.base.activity.WebDetailActivity
import com.worldpeak.chnsmilead.bbs.adapter.BBSItemViewBinder
import com.worldpeak.chnsmilead.bbs.model.BBSItem
import com.worldpeak.chnsmilead.bbs.viewmodel.BBSViewModel
import com.worldpeak.chnsmilead.databinding.FragmentBbsBinding
import com.worldpeak.chnsmilead.home.adapter.ImageAdapter
import com.worldpeak.chnsmilead.home.model.AdminPublicPictureApp
import com.worldpeak.chnsmilead.home.model.BannerData
import com.worldpeak.chnsmilead.util.StatusBarUtil
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator

/**
 * 专家论坛
 */
class BbsFragment : BaseVmVBFragment<BBSViewModel, FragmentBbsBinding>() {

    private val mBBSList: MutableList<BBSItem> = mutableListOf()
    private val mBBSAdapter = MultiTypeAdapter().apply {
        register(BBSItem::class.java, BBSItemViewBinder(context) {
            WebDetailActivity.startActivity(activity,"专家论坛",it.pageUrl)
        })
        items = mBBSList
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_bbs
    }

    override fun initView() {
        super.initView()
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setDarkMode(activity)
    }

    override fun loadData() {
        mViewModel.getBannerList()
        mViewModel.getBBsList(1)
        mViewModel.bbsList.observe(this, Observer {
            mBBSList.clear()
            mBBSList.addAll(it)
            mBinding.rvBbs.layoutManager = LinearLayoutManager(context)
            mBinding.rvBbs.adapter = mBBSAdapter
        })
        mViewModel.bannerInfo.observe(this, Observer {
            initBanner(it.adminPublicPictureApps)
        })
    }

    private fun initBanner(list: List<AdminPublicPictureApp>?) {
        list?.map { BannerData(picUrl = it.imgUrl,title="专家论坛", jumpUrl = it.linkUrl) }?.let { urlList ->
            mBinding.banner.addBannerLifecycleObserver(this) //添加生命周期观察者
                    .setAdapter(ImageAdapter(context, urlList, 8))
                    .setIndicator(CircleIndicator(context))
                    .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                    .setIndicatorSelectedColor(Color.parseColor("#FFFFFF"))
                    .setIndicatorMargins(IndicatorConfig.Margins(0, 0, 80, 30))
                    .setLoopTime(4000)
        }
    }

}