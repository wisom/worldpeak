package com.worldpeak.chnsmilead.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.worldpeak.chnsmilead.base.EmptyFragment

class HomeViewPagerAdapter(fragmentActivity: FragmentActivity, val fragmentList: List<Fragment>) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList.getOrNull(position)?:EmptyFragment()
    }
}