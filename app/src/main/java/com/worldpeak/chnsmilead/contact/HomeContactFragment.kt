package com.worldpeak.chnsmilead.contact

import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseViewModel
import com.worldpeak.chnsmilead.base.BaseVmVBFragment
import com.worldpeak.chnsmilead.databinding.FragmentHomeContactBinding
import com.worldpeak.chnsmilead.main.adapter.HomeViewPagerAdapter
import com.worldpeak.chnsmilead.oa.fragment.OaMsgFragment
import com.worldpeak.chnsmilead.oa.fragment.OaContactFragment
import com.worldpeak.chnsmilead.util.StatusBarUtil
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener

/**
 * 通讯录
 */
class HomeContactFragment : BaseVmVBFragment<BaseViewModel, FragmentHomeContactBinding>() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_home_contact
    }

    override fun initView() {
        super.initView()
        initViewPager()
    }

    private fun initViewPager() {
        var mFragmentList = mutableListOf<Fragment>()
        mFragmentList.add(OaMsgFragment())
        mFragmentList.add(OaContactFragment())
        activity?.let {
            mBinding.vpContact.adapter = HomeViewPagerAdapter(it, mFragmentList)
            mBinding.vpContact.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    switchTab(position)
                }
            })
        }
        mBinding.tvMsg.setPreventDoubleClickListener {
            mBinding.vpContact.currentItem = 0
        }
        mBinding.tvGroup.setPreventDoubleClickListener {
            mBinding.vpContact.currentItem = 1
        }
    }

    fun switchTab(index: Int) {
        when (index) {
            0 -> {
                mBinding.tvMsg.setTextColor(Color.parseColor("#027AFF"))
                mBinding.tvGroup.setTextColor(Color.parseColor("#000000"))
            }
            1 -> {
                mBinding.tvGroup.setTextColor(Color.parseColor("#027AFF"))
                mBinding.tvMsg.setTextColor(Color.parseColor("#000000"))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(activity)
    }

    override fun loadData() {
    }

}