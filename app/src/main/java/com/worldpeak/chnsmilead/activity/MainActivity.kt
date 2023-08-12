package com.worldpeak.chnsmilead.activity

import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.bbs.fragment.BbsFragment
import com.worldpeak.chnsmilead.contact.HomeContactFragment
import com.worldpeak.chnsmilead.databinding.ActivityMainBinding
import com.worldpeak.chnsmilead.home.dialog.CommonConfirmDialog
import com.worldpeak.chnsmilead.home.fragment.HomeFragment
import com.worldpeak.chnsmilead.home.model.LogoConfig
import com.worldpeak.chnsmilead.home.viewmodel.MainViewModel
import com.worldpeak.chnsmilead.main.adapter.HomeViewPagerAdapter
import com.worldpeak.chnsmilead.main.model.MainTab
import com.worldpeak.chnsmilead.mine.MineFragment
import com.worldpeak.chnsmilead.oa.OaFragment
import com.worldpeak.chnsmilead.util.NotificationUtil
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener

class MainActivity : BaseVmVBActivity<MainViewModel, ActivityMainBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        initTab()
        initViewPager()
        initPromission()
    }

    private fun initPromission() {
        NotificationUtil.checkNotificationEnable(this)
    }

    private fun initTab() {
        mBinding.clHome.setPreventDoubleClickListener {
            switchTab(MainTab.HOME)
        }
        mBinding.clCommunity.setPreventDoubleClickListener {
            switchTab(MainTab.COMMUNITY)
        }
        mBinding.clOa.setPreventDoubleClickListener {
            switchTab(MainTab.OA)
        }
        mBinding.clContact.setPreventDoubleClickListener {
            switchTab(MainTab.CONTACT)
        }
        mBinding.clMine.setPreventDoubleClickListener {
            switchTab(MainTab.MINE)
        }
        switchTab(MainTab.HOME)
    }

    private fun initViewPager() {
        var mFragmentList = mutableListOf<Fragment>()
        mFragmentList.add(HomeFragment())
        mFragmentList.add(BbsFragment())
        mFragmentList.add(OaFragment())
        mFragmentList.add(HomeContactFragment())
        mFragmentList.add(MineFragment())
        mBinding.viewpager.adapter = HomeViewPagerAdapter(this, mFragmentList)
        mBinding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                switchTab(MainTab.getTab(position))
            }
        })
    }

    private val selectColor = Color.parseColor("#1D6FE9")
    private val normalColor = Color.parseColor("#66000000")
    private fun switchTab(tab: MainTab) {
        mBinding.viewpager.currentItem = tab.ordinal
        when (tab) {
            MainTab.HOME -> {
                mBinding.ivTabHome.isActivated = true
                mBinding.tvTabHome.setTextColor(selectColor)
                mBinding.ivTabCommunity.isActivated = false
                mBinding.tvTabCommunity.setTextColor(normalColor)
                mBinding.ivTabOa.isActivated = false
                mBinding.tvTabOa.setTextColor(normalColor)
                mBinding.ivTabContact.isActivated = false
                mBinding.tvTabContact.setTextColor(normalColor)
                mBinding.ivTabMine.isActivated = false
                mBinding.tvTabMine.setTextColor(normalColor)
            }
            MainTab.COMMUNITY -> {
                mBinding.ivTabHome.isActivated = false
                mBinding.tvTabHome.setTextColor(normalColor)
                mBinding.ivTabCommunity.isActivated = true
                mBinding.tvTabCommunity.setTextColor(selectColor)
                mBinding.ivTabOa.isActivated = false
                mBinding.tvTabOa.setTextColor(normalColor)
                mBinding.ivTabContact.isActivated = false
                mBinding.tvTabContact.setTextColor(normalColor)
                mBinding.ivTabMine.isActivated = false
                mBinding.tvTabMine.setTextColor(normalColor)
            }
            MainTab.OA -> {
                mBinding.ivTabHome.isActivated = false
                mBinding.tvTabHome.setTextColor(normalColor)
                mBinding.ivTabCommunity.isActivated = false
                mBinding.tvTabCommunity.setTextColor(normalColor)
                mBinding.ivTabOa.isActivated = true
                mBinding.tvTabOa.setTextColor(selectColor)
                mBinding.ivTabContact.isActivated = false
                mBinding.tvTabContact.setTextColor(normalColor)
                mBinding.ivTabMine.isActivated = false
                mBinding.tvTabMine.setTextColor(normalColor)
            }
            MainTab.CONTACT -> {
                mBinding.ivTabHome.isActivated = false
                mBinding.tvTabHome.setTextColor(normalColor)
                mBinding.ivTabCommunity.isActivated = false
                mBinding.tvTabCommunity.setTextColor(normalColor)
                mBinding.ivTabOa.isActivated = false
                mBinding.tvTabOa.setTextColor(normalColor)
                mBinding.ivTabContact.isActivated = true
                mBinding.tvTabContact.setTextColor(selectColor)
                mBinding.ivTabMine.isActivated = false
                mBinding.tvTabMine.setTextColor(normalColor)
            }
            MainTab.MINE -> {
                mBinding.ivTabHome.isActivated = false
                mBinding.tvTabHome.setTextColor(normalColor)
                mBinding.ivTabCommunity.isActivated = false
                mBinding.tvTabCommunity.setTextColor(normalColor)
                mBinding.ivTabOa.isActivated = false
                mBinding.tvTabOa.setTextColor(normalColor)
                mBinding.ivTabContact.isActivated = false
                mBinding.tvTabContact.setTextColor(normalColor)
                mBinding.ivTabMine.isActivated = true
                mBinding.tvTabMine.setTextColor(selectColor)
            }
        }
    }

    override fun loadData() {
        mViewModel.getLogo()
        mViewModel.logoList.observe(this, Observer { logoList ->
            val configVal = logoList.getOrNull(0)?.configVal
            if (!configVal.isNullOrEmpty()) {
                val config = Gson().fromJson(configVal, LogoConfig::class.java)
                if (config.topImg.isNullOrEmpty()) {
                    mBinding.ivTabOa.setImageResource(R.drawable.selector_oa_tab)
                } else {
                    Glide.with(this).load(config.topImg).into(mBinding.ivTabOa)
                }
            }
        })
    }

    override fun onBackPressed() {
        val dialog = CommonConfirmDialog(this, "确认退出吗？", "取消", "确定", {}, {
            finish()
        })
        dialog.show()
    }
}