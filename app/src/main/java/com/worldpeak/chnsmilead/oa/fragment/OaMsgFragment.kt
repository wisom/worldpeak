package com.worldpeak.chnsmilead.oa.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBFragment
import com.worldpeak.chnsmilead.contact.adapter.ContactViewBinder
import com.worldpeak.chnsmilead.contact.model.ContactItem
import com.worldpeak.chnsmilead.databinding.FragmentOaMsgBinding
import com.worldpeak.chnsmilead.oa.viewmodel.OaMsgViewModel

/**
 * 消息
 */
class OaMsgFragment : BaseVmVBFragment<OaMsgViewModel, FragmentOaMsgBinding>() {

    companion object {
    }

    private val mDataList: MutableList<ContactItem> = mutableListOf()
    private val mAdapter = MultiTypeAdapter().apply {
        register(ContactItem::class.java, ContactViewBinder(context))
        items = mDataList
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_oa_msg
    }

    override fun initView() {
        super.initView()

        mViewModel.contactList.observe(this) {
            if (it != null) {
                mAdapter.items = it
            }
        }

        mBinding.rvContact.layoutManager = LinearLayoutManager(activity)
        mBinding.rvContact.adapter = mAdapter
    }

    override fun loadData() {
    }

}