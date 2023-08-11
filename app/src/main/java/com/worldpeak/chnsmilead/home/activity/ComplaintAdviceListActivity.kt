package com.worldpeak.chnsmilead.home.activity

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityComplaintAdviceListBinding
import com.worldpeak.chnsmilead.home.adapter.ComplaintAdviceListViewBinder
import com.worldpeak.chnsmilead.home.adapter.ImageAdapter
import com.worldpeak.chnsmilead.home.model.AdminPublicPictureApp
import com.worldpeak.chnsmilead.home.model.BannerData
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceItem
import com.worldpeak.chnsmilead.home.viewmodel.ComplaintAdviceListViewModel
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator

/**
 * 投诉建议列表
 */
class ComplaintAdviceListActivity : BaseVmVBActivity<ComplaintAdviceListViewModel, ActivityComplaintAdviceListBinding>() {

    private val mTypeList: MutableList<ComplaintAdviceItem> = mutableListOf()
    private val mAdapter = MultiTypeAdapter().apply {
        register(ComplaintAdviceItem::class.java, ComplaintAdviceListViewBinder(this@ComplaintAdviceListActivity) {
            val intent = Intent(this@ComplaintAdviceListActivity, ComplaintAdviceDetailActivity::class.java)
            intent.putExtra(ComplaintAdviceDetailActivity.EXTRA_ITEM, it)
            startActivity(intent)
        })
        items = mTypeList
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_complaint_advice_list
    }

    override fun initView() {
        super.initView()
        mBinding.tvAdd.setPreventDoubleClickListener {
            startActivity(Intent(this@ComplaintAdviceListActivity, ComplaintAdviceAddActivity::class.java))
        }
        mBinding.rvComplaintAdviceList.layoutManager = LinearLayoutManager(this)
        mBinding.rvComplaintAdviceList.adapter = mAdapter
    }

    private fun initBanner(list: List<AdminPublicPictureApp>?) {
        list?.map { BannerData(picUrl = it.imgUrl, title = "详情", jumpUrl = it.linkUrl) }?.let { urlList ->
            mBinding.banner.addBannerLifecycleObserver(this) //添加生命周期观察者
                    .setAdapter(ImageAdapter(this, urlList, 8))
                    .setIndicator(CircleIndicator(this))
                    .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                    .setIndicatorSelectedColor(Color.parseColor("#FFFFFF"))
                    .setIndicatorMargins(IndicatorConfig.Margins(0, 0, 80, 30))
                    .setLoopTime(4000)
        }
    }

    override fun loadData() {
        mViewModel.getBannerList()
        mViewModel.bannerInfo.observe(this, Observer {
            initBanner(it.adminPublicPictureApps)
        })
        //类型（1：建议，2：投诉）
        mViewModel.getComplaintAdviceList("", "1")
        mViewModel.dataList.observe(this) {
            mTypeList.clear()
            if (!it.isNullOrEmpty()) {
                mTypeList.addAll(it)
            }
            mAdapter.notifyDataSetChanged()
        }
    }
}