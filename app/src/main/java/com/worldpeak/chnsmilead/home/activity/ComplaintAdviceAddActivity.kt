package com.worldpeak.chnsmilead.home.activity

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.text.Html
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.drakeet.multitype.MultiTypeAdapter
import com.worldpeak.chnsmilead.MyApp
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.base.BaseVmVBActivity
import com.worldpeak.chnsmilead.databinding.ActivityComplaintAdviceAddBinding
import com.worldpeak.chnsmilead.home.adapter.CommonPicViewBinder
import com.worldpeak.chnsmilead.home.model.*
import com.worldpeak.chnsmilead.home.viewmodel.ComplaintAdviceViewModel
import com.worldpeak.chnsmilead.interfaces.IUploadListener
import com.worldpeak.chnsmilead.mine.dialog.ListSelectConfirmDialog
import com.worldpeak.chnsmilead.util.LogUtil
import com.worldpeak.chnsmilead.util.PermissionUtil
import com.worldpeak.chnsmilead.util.UriUtils
import com.worldpeak.chnsmilead.util.setPreventDoubleClickListener
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 新建投诉建议
 */
class ComplaintAdviceAddActivity : BaseVmVBActivity<ComplaintAdviceViewModel, ActivityComplaintAdviceAddBinding>() {

    companion object {
        const val REQUEST_TAKE_PHOTOES = 0x22
        const val TAG = "ComplaintAdviceAddActivity=="
    }

    private val mPicList: MutableList<PicInfo> = mutableListOf()
    private val mPicAdapter = MultiTypeAdapter().apply {
        register(PicInfo::class.java, CommonPicViewBinder(this@ComplaintAdviceAddActivity, {
            val gallery = Intent(Intent.ACTION_PICK)
            gallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(gallery, REQUEST_TAKE_PHOTOES)
        }, {
            mPicList.remove(it)
            notifyDataSetChanged()
        }))
        items = mPicList
    }

    //业务类型列表
    private val mBusinessTypeList: MutableList<ComplaintAdviceBusinessType> = mutableListOf()

    //分类内容列表
    private val mContentTypeList: MutableList<ComplaintAdviceType> = mutableListOf()

    //受理单位列表
    private val mUnitList: MutableList<ComplaintAdviceUnitItem> = mutableListOf()


    override fun getLayoutId(): Int {
        return R.layout.activity_complaint_advice_add
    }

    private var currBusinessType: ComplaintAdviceBusinessType? = null//业务类型（1：建议，2：投诉）
    private var businessTypeDialog: ListSelectConfirmDialog? = null

    private var currContentType: ComplaintAdviceType? = null//当前选择的分类内容
    private var contentTypeDialog: ListSelectConfirmDialog? = null

    private var currUnit: ComplaintAdviceUnitItem? = null//当前选择的受理单位
    private var unitDialog: ListSelectConfirmDialog? = null

    override fun initView() {
        super.initView()
        PermissionUtil.check(this)
        mBinding.tvServiceUnit.setPreventDoubleClickListener {
            val list = mUnitList.map { it.schoolName }?.filterNotNull()
            val curIndex = mUnitList.indexOfFirst { it.schoolName == mBinding.tvServiceUnit.text.toString() }
            if (list.isNotEmpty()) {
                unitDialog = ListSelectConfirmDialog(this@ComplaintAdviceAddActivity, list, if (curIndex == -1) 0 else curIndex, false) { index ->
                    currUnit = mUnitList.getOrNull(index)
                    mBinding.tvServiceUnit.text = currUnit?.schoolName ?: ""
                }
                unitDialog?.show()
            }
        }
        //业务类型（1：建议，2：投诉）
        mBusinessTypeList.add(ComplaintAdviceBusinessType("建议", 1))
        mBusinessTypeList.add(ComplaintAdviceBusinessType("投诉", 2))
        mBinding.tvBusinessType.setPreventDoubleClickListener {
            val list = mBusinessTypeList.map { it.content }?.filterNotNull()
            val curIndex = mBusinessTypeList.indexOfFirst { it.content == mBinding.tvBusinessType.text.toString() }
            if (list.isNotEmpty()) {
                businessTypeDialog = ListSelectConfirmDialog(this@ComplaintAdviceAddActivity, list, if (curIndex == -1) 0 else curIndex, false) { index ->
                    currBusinessType = mBusinessTypeList.getOrNull(index)
                    mBinding.tvBusinessType.text = currBusinessType?.content ?: ""
                }
                businessTypeDialog?.show()
            }
        }
        mBinding.tvContentType.setPreventDoubleClickListener {
            val list = mContentTypeList.map { it.complaintProposeName }?.filterNotNull()
            val curIndex = mContentTypeList.indexOfFirst { it.complaintProposeName == mBinding.tvContentType.text.toString() }
            if (list.isNotEmpty()) {
                contentTypeDialog = ListSelectConfirmDialog(this@ComplaintAdviceAddActivity, list, if (curIndex == -1) 0 else curIndex, false) { index ->
                    currContentType = mContentTypeList.getOrNull(index)
                    mBinding.tvContentType.text = currContentType?.complaintProposeName ?: ""
                }
                contentTypeDialog?.show()
            }
        }
        mPicList.add(PicInfo(uri = null, isAdd = true))
        mBinding.rvImg.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mBinding.rvImg.adapter = mPicAdapter
        mBinding.tvSubmit.setPreventDoubleClickListener {
            if (currUnit == null) {
                ToastUtils.showShort("受理单位不能为空")
                return@setPreventDoubleClickListener
            }
            if (mBusinessTypeList == null) {
                ToastUtils.showShort("选择类型不能为空")
                return@setPreventDoubleClickListener
            }
            if (currContentType == null) {
                ToastUtils.showShort("分类内容不能为空")
                return@setPreventDoubleClickListener
            }
            val fileList = mutableListOf<ComplaintAdviceFileItem>()
            val title = mBinding.tvSubTitle.text.toString()
            val desc = mBinding.tvDesc.text.toString()
            val complainter = mBinding.etComplainter.text.toString()
            val contact = mBinding.etContact.text.toString()
            if (title.isNullOrEmpty()) {
                ToastUtils.showShort("标题不能为空")
                return@setPreventDoubleClickListener
            }
            if (desc.isNullOrEmpty()) {
                ToastUtils.showShort("内容不能为空")
                return@setPreventDoubleClickListener
            }
            if (contact.isNullOrEmpty()) {
                ToastUtils.showShort("联系方式不能为空")
                return@setPreventDoubleClickListener
            }
            val realPicList = mPicList.filter { it.isAdd == false }
            if (!realPicList.isNullOrEmpty()) {
                uploadFiles { result ->
                    if (result.first) {
                        submit(
                                title = title,
                                desc = desc,
                                complainter = complainter,
                                contact = contact,
                                fileList = result.second
                        )
                    }
                }
            } else {
                submit(
                        title = title,
                        desc = desc,
                        complainter = complainter,
                        contact = contact,
                        fileList = null
                )
            }
        }
    }

    private fun uploadFiles(callback: (Pair<Boolean, List<ComplaintAdviceFileItem>>) -> Unit) {
        val fileList = mutableListOf<ComplaintAdviceFileItem>()
        val requestList = mutableListOf<Deferred<Int>>()
        lifecycleScope.launch {
            mPicList.filter { it.isAdd == false }.forEach { picInfo ->
                val request = async(Dispatchers.IO) {
                    suspendCoroutine<Int> {
                        mViewModel.uploadFile(UriUtils.getPath(MyApp.getContext(), picInfo.uri), object : IUploadListener {
                            override fun onUploadFail() {
                                it.resume(0)
                            }

                            override fun onUploadSuccess(file: CommonAccessory?) {
                                if (file != null) {
                                    fileList.add(ComplaintAdviceFileItem(
                                            fileContent = "",
                                            fileContentType = "",
                                            fileDes = file.fileBucket,
                                            fileId = file.id,
                                            fileName = file.fileObjectName,
                                            fileSize = file.fileSizeKb,
                                            fileType = file.fileSuffix,
                                            fileUrl = file.fileUrl
                                    ))
                                }
                                it.resume(1)
                            }
                        })
                    }
                }
                requestList.add(request)
            }
            var count = 0
            requestList.forEach { deferred ->
                count += deferred.await()
            }
            LogUtil.d(TAG, "count=${count} requestList.size=${requestList.size}")
            callback.invoke(Pair(count != 0 && count >= requestList.size,fileList))
        }
    }

    private fun submit(title: String, desc: String, complainter: String, contact: String, fileList: List<ComplaintAdviceFileItem>?) {
        mViewModel.submit(
                currBusinessType?.businessType ?: 1,
                currUnit?.schoolId ?: "",
                currUnit?.schoolName ?: "",
                currUnit?.schoolType ?: 0,
                currContentType?.complaintProposeType ?: 0,
                currContentType?.complaintProposeName ?: "",
                title,
                desc,
                complainter,
                contact,
                fileList
        )
    }

    override fun loadData() {
        mViewModel.getStatement()
        mViewModel.statement.observe(this, Observer {
            mBinding.tvTip.text = Html.fromHtml(it.cpDesc)
        })

        mViewModel.getServiceUnitList()
        mViewModel.unitList.observe(this, Observer {
            mUnitList.clear()
            mUnitList.addAll(it)
        })

        mViewModel.getComplaintAdviceType(1)
        mViewModel.typeList.observe(this) {
            mContentTypeList.clear()
            if (!it.isNullOrEmpty()) {
                mContentTypeList.addAll(it)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (REQUEST_TAKE_PHOTOES == requestCode && resultCode === RESULT_OK && data.data != null) {
                val uri: Uri? = data.data
                mPicList.add(mPicList.size - 1, PicInfo(uri = uri, isAdd = false))
                mPicAdapter.notifyDataSetChanged()
            }
        }
    }
}