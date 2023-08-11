package com.worldpeak.chnsmilead.home.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBFragment
import com.worldpeak.chnsmilead.base.activity.WebDetailActivity
import com.worldpeak.chnsmilead.base.manager.AccountManager
import com.worldpeak.chnsmilead.databinding.FragmentHomeBinding
import com.worldpeak.chnsmilead.home.activity.ComplaintAdviceListActivity
import com.worldpeak.chnsmilead.home.activity.DynamicListActivity
import com.worldpeak.chnsmilead.home.adapter.AreaDynamicViewBinder
import com.worldpeak.chnsmilead.home.adapter.AreaPicViewBinder
import com.worldpeak.chnsmilead.home.adapter.ImageAdapter
import com.worldpeak.chnsmilead.home.model.AdminPublicPictureApp
import com.worldpeak.chnsmilead.home.model.BannerData
import com.worldpeak.chnsmilead.home.model.DynamicItem
import com.worldpeak.chnsmilead.home.viewmodel.HomeViewModel
import com.worldpeak.chnsmilead.oa.activity.DocumentApprovalListActivity
import com.worldpeak.chnsmilead.util.DateUtil
import com.worldpeak.chnsmilead.util.StatusBarUtil
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : BaseVmVBFragment<HomeViewModel, FragmentHomeBinding>() {

    private val mDynamicList: MutableList<DynamicItem> = mutableListOf()
    private val mDynamicAdapter = MultiTypeAdapter().apply {
        register(DynamicItem::class.java, AreaDynamicViewBinder(context) {
            WebDetailActivity.startActivity(context, "区域教育动态", it.pageUrl)
        })
        items = mDynamicList
    }

    private val mPicList: MutableList<DynamicItem> = mutableListOf()
    private val mPicAdapter = MultiTypeAdapter().apply {
        register(DynamicItem::class.java, AreaPicViewBinder(context) {
            WebDetailActivity.startActivity(activity, "区域学校风采", it.pageUrl)
        })
        items = mPicList
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        val user = AccountManager.getUser()
        mBinding.tvName.text = user.name + (user?.loginEmpInfo?.orgName ?: "") + "，${getTimeStr()}好"
        mBinding.tvTime.text = "今日是" + DateUtil.getMMdd(System.currentTimeMillis()) + " ${DateUtil.getWeek(Date())}"
        mBinding.llAllDynamic.setPreventDoubleClickListener {
            activity?.let { it1 -> DynamicListActivity.startActivitiy(it1, 1, "区域教育动态", mPicList) }
        }
        mBinding.llSchoolStyle.setPreventDoubleClickListener {
            activity?.let { it1 -> DynamicListActivity.startActivitiy(it1, 2, "区域学校风采", mPicList) }
        }
        mBinding.ivComplaint.setPreventDoubleClickListener {
            startActivity(Intent(context, ComplaintAdviceListActivity::class.java))
        }
        mBinding.rlMsg.setPreventDoubleClickListener {
            activity?.let { it1 -> DocumentApprovalListActivity.startActivity(it1,1) }
        }
    }

    private fun getTimeStr(): String {
        val date = Date()
        val format = SimpleDateFormat("a")
        val time: String = format.format(date)
        return if (time.contains("AM")) {
            "上午"
        } else {
            "下午"
        }
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setDarkMode(activity)
    }

    override fun loadData() {
        mViewModel.getAreaDynamicInfo()
        mViewModel.areaDynamicInfo.observe(this, Observer {
            mDynamicList.clear()
            it.adminArticleApps?.let { it1 -> mDynamicList.addAll(it1) }
            mBinding.rvDynamic.layoutManager = LinearLayoutManager(context)
            mBinding.rvDynamic.adapter = mDynamicAdapter
            initBanner(it.adminPublicPictureApps)
        })
        mViewModel.getSchoolStyleList(1)
        mViewModel.areaPicInfo.observe(this, Observer {
            mPicList.clear()
            mPicList.addAll(it)
            mBinding.rvSchoolStyle.layoutManager = GridLayoutManager(context, 3)
            mBinding.rvSchoolStyle.adapter = mPicAdapter
        })
    }

    private fun initBanner(list: List<AdminPublicPictureApp>?) {
        list?.map { BannerData(picUrl = it.imgUrl, title = "详情", jumpUrl = it.linkUrl) }?.let { urlList ->
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