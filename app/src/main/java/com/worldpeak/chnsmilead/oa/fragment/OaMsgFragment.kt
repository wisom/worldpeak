package com.worldpeak.chnsmilead.oa.fragment

import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBFragment
import com.worldpeak.chnsmilead.databinding.FragmentOaMsgBinding
import com.worldpeak.chnsmilead.oa.viewmodel.OaMsgViewModel

/**
 * 消息
 */
class OaMsgFragment : BaseVmVBFragment<OaMsgViewModel, FragmentOaMsgBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_oa_msg
    }

    override fun loadData() {

    }

}