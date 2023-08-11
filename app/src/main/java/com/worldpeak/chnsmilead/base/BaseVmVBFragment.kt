package com.worldpeak.chnsmilead.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseVmVBFragment<VM : BaseViewModel, VB : ViewBinding> : BaseVBFragment<VB>() {

    protected val mViewModel: VM by lazy {
        val types = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        val clazz = types[0] as Class<VM>
        val factory= object : ViewModelProvider.Factory {
            override fun <VM: ViewModel?> create(modelClass: Class<VM>): VM {
                return clazz.newInstance() as VM
            }
        }
        ViewModelProvider(this,factory).get<VM>(types[0] as Class<VM>)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initBaseViewModel()
        loadData()
    }

    private fun initBaseViewModel() {
//        mViewModel.mIsShowLoading.observe(this, Observer {
//            it?.let {
//                showLoading(it)
//            }
//        })
//
//        mViewModel.mIsShowEmpty.observe(this, Observer {
//            it?.let {
//                showEmpty(it)
//            }
//        })
//
//        mViewModel.mIsShowNetError.observe(this, Observer {
//            it?.let {
//                showNetErr(it)
//            }
//        })
//
//        mViewModel.mIsShowNoMore.observe(this, Observer {
//            it?.let {
//                showNoMore(it)
//            }
//        })
//        mViewModel.mIsShowLoadError.observe(this, Observer {
//            it?.let {
//                showLoadErr(it.isLoadError, it.code)
//            }
//        })
    }


}