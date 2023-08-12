package com.worldpeak.chnsmilead.oa.fragment

import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBFragment
import com.worldpeak.chnsmilead.databinding.FragmentOaContactBinding
import com.worldpeak.chnsmilead.oa.activity.GroupListActivity
import com.worldpeak.chnsmilead.oa.activity.contact.OaContactOrgActivity
import com.worldpeak.chnsmilead.oa.viewmodel.OaContactViewModel
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener

class OaContactFragment : BaseVmVBFragment<OaContactViewModel, FragmentOaContactBinding>(),
    View.OnClickListener {

    companion object {
        const val EXTRA_CAN_EDIT = "EXTRA_CAN_EDIT"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_oa_contact
    }

    override fun initView() {
        super.initView()
        val canEdit = arguments?.getBoolean(EXTRA_CAN_EDIT) ?: false
        mBinding.llOrg.setPreventDoubleClickListener {
            activity?.let { it1 -> OaContactOrgActivity.startActivity(it1, canEdit) }
        }

        mBinding.llGroup.setOnClickListener(this)
    }

    override fun loadData() {
        mViewModel.contactList.observe(this) {
            if (it != null && it.isNotEmpty()) {

            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.llGroup -> startActivity(Intent(activity, GroupListActivity::class.java))
        }
    }
}