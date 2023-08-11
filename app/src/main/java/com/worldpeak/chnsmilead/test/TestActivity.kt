package com.worldpeak.chnsmilead.test

import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityTestBinding
import com.worldpeak.chnsmilead.net.CommonRequest

class TestActivity : BaseVmVBActivity<TestViewModel, ActivityTestBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initView() {
        super.initView()
        mBinding.test.setOnClickListener {
            mViewModel.testLogin("13580302214", "123456")
        }
    }

    override fun loadData() {
    }
}