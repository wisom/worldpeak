package com.worldpeak.chnsmilead.base

import androidx.lifecycle.ViewModel
import com.drake.tooltip.toast
import com.worldpeak.chnsmilead.util.ThreadUtil
import com.worldpeak.chnsmilead.util.ThreadUtils

open class BaseViewModel : ViewModel() {
//    var mIsShowLoading: MutableLiveData<Boolean> = MutableLiveData()
//    var mIsShowNetError: MutableLiveData<Boolean> = MutableLiveData()
//    var mIsShowLoadError: MutableLiveData<LoadError> = MutableLiveData()
//    var mIsShowEmpty: MutableLiveData<Boolean> = MutableLiveData()
//    var mIsShowNoMore: MutableLiveData<Boolean> = MutableLiveData()
//    private var cancelableList: MutableList<ICancelable>? = null
//
//    protected fun addCancelable(cancelable: ICancelable?) {
//        if (cancelableList == null) {
//            cancelableList = ArrayList()
//        }
//        if (cancelable != null) cancelableList?.add(cancelable)
//    }
//
//    private fun cancel() {
//        cancelableList?.let {
//            for (iCancelable in cancelableList!!) {
//                iCancelable.cancel()
//            }
//        }
//    }

    override fun onCleared() {
        super.onCleared()
//        cancel()
    }

    fun showToast(message: String) {
        ThreadUtil.runOnUiThread(Runnable { toast(message) })
    }

    fun showToast(message: Int) {
        ThreadUtil.runOnUiThread(Runnable { toast(message) })
    }
}