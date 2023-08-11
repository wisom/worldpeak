package com.worldpeak.chnsmilead.home.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Parcelable
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.base.activity.WebDetailActivity
import com.worldpeak.chnsmilead.databinding.ActivityDynamicListBinding
import com.worldpeak.chnsmilead.home.adapter.ImageAdapter
import com.worldpeak.chnsmilead.home.adapter.SchoolStyleListViewBinder
import com.worldpeak.chnsmilead.home.model.BannerData
import com.worldpeak.chnsmilead.home.model.DynamicItem
import com.worldpeak.chnsmilead.home.viewmodel.DynamicListViewModel
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator

/**
 * 学校风采列表
 */
class DynamicListActivity : BaseVmVBActivity<DynamicListViewModel, ActivityDynamicListBinding>() {

    private val mBannerList by lazy {
        intent.getParcelableArrayExtra(EXTRA_BANNER)
    }

    private val mTitle by lazy {
        intent.getStringExtra(EXTRA_TITLE)
    }

    private val mType by lazy {
        intent.getIntExtra(EXTRA_TYPE, 1)
    }

    companion object {
        const val EXTRA_BANNER = "EXTRA_BANNER"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_TYPE = "EXTRA_TYPE"//1:区域教育动态；2：区域学校风采

        /**
         * @param type 1:区域教育动态；2：区域学校风采
         */
        fun startActivitiy(context: Activity, type: Int, title: String, list: List<Parcelable>) {
            val intent = Intent(context, DynamicListActivity::class.java)
            intent.putExtra(EXTRA_TYPE, type)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_BANNER, list.toTypedArray())
            context.startActivity(intent)
        }
    }

    private val mTypeList: MutableList<DynamicItem> = mutableListOf()
    private val mAdapter = MultiTypeAdapter().apply {
        register(DynamicItem::class.java, SchoolStyleListViewBinder(this@DynamicListActivity) {
            WebDetailActivity.startActivity(this@DynamicListActivity, getTitleStr(), it.pageUrl)
        })
        items = mTypeList
    }

    private fun getTitleStr(): String {
        return when (mType) {
            2 -> {
                "区域学校风采"
            }
            else -> {
                "区域教育动态"
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_dynamic_list
    }

    override fun initView() {
        super.initView()
        mTitle?.let { mBinding.titleBar.setTitle(it) }
        mBinding.rvComplaintAdviceList.layoutManager = LinearLayoutManager(this)
        mBinding.rvComplaintAdviceList.adapter = mAdapter
        initBanner()
    }

    private fun initBanner() {
        mBannerList?.map { BannerData(picUrl = (it as? DynamicItem)?.topImg, title = "区域学校风采", jumpUrl = (it as? DynamicItem)?.pageUrl) }?.let { urlList ->
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
        mViewModel.getDataList(mType, 1)
        mViewModel.dataList.observe(this) {
            mTypeList.clear()
            if (!it.isNullOrEmpty()) {
                mTypeList.addAll(it)
            }
            mAdapter.notifyDataSetChanged()
        }
    }
}