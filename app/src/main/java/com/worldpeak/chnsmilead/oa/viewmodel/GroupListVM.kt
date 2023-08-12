package com.worldpeak.chnsmilead.oa.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.tencent.imsdk.v2.V2TIMGroupInfo
import com.tencent.imsdk.v2.V2TIMGroupListener
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMValueCallback
import com.worldpeak.chnsmilead.base.BaseViewModel


/*
 * @author kazeik , QQ/WX:77132995, email:kazeik@163.com
 * app，uniapp , 微信小程序，java, go , admin系统开发。
 * 2023/8/12 19 25
 * 类说明:
 */

class GroupListVM : BaseViewModel() {
    val groupList: MutableLiveData<List<V2TIMGroupInfo>> by lazy { MutableLiveData() }

    init {
        initListener()
        getGroupList()
    }

    private fun initListener() {
        V2TIMManager.getInstance().addGroupListener(object : V2TIMGroupListener() {
            override fun onGroupCreated(groupID: String) {
                getGroupList()
            }
        })
    }


    fun getGroupList() {
        V2TIMManager.getGroupManager()
            .getJoinedGroupList(object : V2TIMValueCallback<List<V2TIMGroupInfo>> {
                override fun onSuccess(v2TIMGroupInfos: List<V2TIMGroupInfo>) {
                    if (v2TIMGroupInfos.isEmpty()) {
                        return
                    }
                    // 获取已加入的群组列表成功
                    groupList.postValue(v2TIMGroupInfos)
                }

                override fun onError(code: Int, desc: String) {
                    // 获取已加入的群组列表失败
                    Log.e("tag", "获取群组列表失败")
                    ToastUtils.showShort("获取群组列表失败")
                }
            })
    }

    fun createGroup(name: String?) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("群组名称不能为空")
            return
        }
        V2TIMManager.getInstance().createGroup(
            V2TIMManager.GROUP_TYPE_WORK,
            null,
            name,
            object : V2TIMValueCallback<String?> {
                override fun onSuccess(s: String?) {
                    getGroupList()
                }

                override fun onError(code: Int, desc: String) {
                    Log.e("tag", "code = $code | desc = $desc")
                    ToastUtils.showShort("群组创建失败: $desc")
                }
            })

    }
}