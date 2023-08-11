package com.worldpeak.chnsmilead.oa.activity.contact

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityOaContactOrgUnitUserBinding
import com.worldpeak.chnsmilead.oa.adapter.*
import com.worldpeak.chnsmilead.oa.model.*
import com.worldpeak.chnsmilead.oa.viewmodel.OaContactViewModel
import com.worldpeak.chnsmilead.util.*

/**
 * oa通讯录组织架构单位-用户详情
 */
class OaContactOrgUnitUserActivity : BaseVmVBActivity<OaContactViewModel, ActivityOaContactOrgUnitUserBinding>() {

    companion object {
        const val EXTRA_REQ_CODE = 0x222
        const val EXTRA_RESULT = "EXTRA_RESULT"
        const val EXTRA_DATA = "EXTRA_DATA"
        const val EXTRA_CANEDIT = "EXTRA_CANEDIT"

        fun startActivity(context: Activity, data: SchoolItem, canEdit: Boolean) {
            val intent = Intent(context, OaContactOrgUnitUserActivity::class.java)
            intent.putExtra(EXTRA_DATA, data)
            intent.putExtra(EXTRA_CANEDIT, canEdit)
            context.startActivityForResult(intent, EXTRA_REQ_CODE)
        }
    }

    private val mSchool by lazy {
        intent.getParcelableExtra<SchoolItem>(EXTRA_DATA)
    }

    private val mCanEdit by lazy {
        intent.getBooleanExtra(EXTRA_CANEDIT, false)
    }

    private val mUserList: MutableList<Person> = mutableListOf()

    override fun getLayoutId(): Int {
        return R.layout.activity_oa_contact_org_unit_user
    }

    override fun initView() {
        super.initView()
        mBinding.titleBar.setTitle(mSchool?.name ?: "")
        mBinding.tvUnit.text = mSchool?.name
        mUserList.clear()
        mSchool?.children?.forEach { department ->
            department.children?.forEach { user ->
                user.department = department.name
                user.isSelect = SelectPersonUtil.getUserList().any { it.id == user.id }
                mUserList.add(user)
            }
        }
        mBinding.rvContact.layoutManager = LinearLayoutManager(this)
        val mContactAdapter = MultiTypeAdapter().apply {
            register(Person::class.java, ContactOrgSchoolUserListViewBinder(mCanEdit, mSchool) { pair ->
                if (mCanEdit) {
                    val index = pair.first
                    val user = pair.second
                    mUserList.firstOrNull { it.id == user.id }?.isSelect = user.isSelect
                    notifyItemChanged(index)
                    updateSelect(user)
                } else {
                    //im聊天
                }
            })
            items = mUserList
        }
        mBinding.rvContact.adapter = mContactAdapter
        if (mCanEdit) {
            mBinding.clSelect.show()
            updateSelect(null)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateSelect(user: Person?) {
        if (user != null) {
            if (user.isSelect == true) {
                SelectPersonUtil.addUser(user)
            } else {
                SelectPersonUtil.removeUser(user)
            }
        }
        mBinding.tvCount.text = "已选择（${SelectPersonUtil.getUserList().size}）人"
        mBinding.tvConfirm.text = "确定（${mUserList.filter { it.isSelect == true }.size}/${SelectPersonUtil.getMaxCount()}）"
        mBinding.tvConfirm.setPreventDoubleClickListener {
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.setLightMode(this)
    }

    @SuppressLint("SetTextI18n")
    override fun loadData() {
    }
}