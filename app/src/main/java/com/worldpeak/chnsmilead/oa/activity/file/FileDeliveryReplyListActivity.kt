package com.worldpeak.chnsmilead.oa.activity.file

import androidx.fragment.app.Fragment
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.base.UseEventBus
import com.worldpeak.chnsmilead.databinding.ActivityFileDeliveryReplyListBinding
import com.worldpeak.chnsmilead.oa.adapter.FragAdapter
import com.worldpeak.chnsmilead.oa.event.FileHasReplyCountEvent
import com.worldpeak.chnsmilead.oa.event.FileNoReplyCountEvent
import com.worldpeak.chnsmilead.oa.fragment.FileHasReplyFragment
import com.worldpeak.chnsmilead.oa.fragment.FileNoReplyFragment
import com.worldpeak.chnsmilead.util.StatusBarUtil
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 文件下发回复列表
 */
@UseEventBus
class FileDeliveryReplyListActivity : BaseVmVBActivity<BaseViewModel, ActivityFileDeliveryReplyListBinding>() {

    private val mFragmentList: MutableList<Fragment> = mutableListOf()
    private val mTabList: MutableList<String> = mutableListOf()

    private val mFormId by lazy {
        intent.getStringExtra(EXTRA_FORMID)
    }

    companion object {
        const val EXTRA_FORMID = "EXTRA_FORMID"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_file_delivery_reply_list
    }

    override fun initView() {
        super.initView()
        mTabList.add("已确认")
        mTabList.add("未确认")

        mTabList.forEach {
            val tab = mBinding.tabLayout.newTab()
            tab.text = it
            mBinding.tabLayout.addTab(tab)
        }
        mFragmentList.add(FileHasReplyFragment(mFormId))
        mFragmentList.add(FileNoReplyFragment(mFormId))
        mBinding.viewPager.adapter = FragAdapter(supportFragmentManager, mTabList, mFragmentList)

        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun loadData() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onHasReplyEvent(event: FileHasReplyCountEvent) {
        mBinding.tabLayout.removeAllTabs()
        val otherStr = mTabList.getOrNull(1)
        mTabList.clear()
        mTabList.add("已确认 " + event.count)
        if (!otherStr.isNullOrEmpty()) {
            mTabList.add(otherStr)
        }
        mTabList.forEach {
            val tab = mBinding.tabLayout.newTab()
            tab.text = it
            mBinding.tabLayout.addTab(tab)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNoReplyEvent(event: FileNoReplyCountEvent) {
        mBinding.tabLayout.removeAllTabs()
        val otherStr = mTabList.getOrNull(0)
        mTabList.clear()
        if (!otherStr.isNullOrEmpty()) {
            mTabList.add(otherStr)
        }
        mTabList.add("未确认 " + event.count)
        mTabList.forEach {
            val tab = mBinding.tabLayout.newTab()
            tab.text = it
            mBinding.tabLayout.addTab(tab)
        }
    }
}