package com.worldpeak.chnsmilead.oa.activity

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityDocumentApprovalListBinding
import com.worldpeak.chnsmilead.home.viewmodel.ComplaintAdviceListViewModel
import com.worldpeak.chnsmilead.oa.adapter.FragAdapter
import com.worldpeak.chnsmilead.oa.fragment.AllProcessFragment
import com.worldpeak.chnsmilead.util.StatusBarUtil

/**
 * 公文审批列表
 */
class DocumentApprovalListActivity : BaseVmVBActivity<BaseViewModel, ActivityDocumentApprovalListBinding>() {

    private val mFragmentList: MutableList<Fragment> = mutableListOf()
    private val mTabList: MutableList<String> = mutableListOf()
    private val selectTab by lazy {
        intent.getIntExtra(EXTRA_TAB_INDEX, 0)
    }

    companion object {
        const val EXTRA_TAB_INDEX = "EXTRA_INDEX"

        fun startActivity(context: Activity, selectTab: Int) {
            val intent = Intent(context, DocumentApprovalListActivity::class.java)
            intent.putExtra(EXTRA_TAB_INDEX, selectTab)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_document_approval_list
    }

    override fun initView() {
        super.initView()
        mTabList.add("全部流程")
        mTabList.add("待批")
        mTabList.add("已审批")
        mTabList.add("我的通知")

        mTabList.forEach {
            val tab = mBinding.tabLayout.newTab()
            tab.text = it
            mBinding.tabLayout.addTab(tab)
        }
        //listType=2：我的审批，3：我的通知
        //queryStatus:审批人状态（1待批/待读、2已批/已读(包含拒批)）,传空时为查所有
        mFragmentList.add(AllProcessFragment("2",""))
        mFragmentList.add(AllProcessFragment("2","1"))
        mFragmentList.add(AllProcessFragment("2","2"))
        mFragmentList.add(AllProcessFragment("3",""))
        mBinding.viewPager.adapter = FragAdapter(supportFragmentManager, mTabList, mFragmentList)

        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)

        mBinding.viewPager.currentItem = selectTab
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    override fun loadData() {
    }
}