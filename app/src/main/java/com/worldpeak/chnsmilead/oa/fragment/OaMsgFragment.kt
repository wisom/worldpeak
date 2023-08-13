package com.worldpeak.chnsmilead.oa.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.tencent.qcloud.tuikit.tuiconversation.classicui.page.TUIConversationFragment
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

    override fun getLayoutId(): Int {
        return R.layout.fragment_oa_msg
    }

    override fun initView() {
        super.initView()

        childFragmentManager.beginTransaction().replace(R.id.flView, TUIConversationFragment())
            .commitAllowingStateLoss()

    }

    override fun loadData() {
    }

}