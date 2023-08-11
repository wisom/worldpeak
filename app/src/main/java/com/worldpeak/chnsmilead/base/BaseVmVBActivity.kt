package com.worldpeak.chnsmilead.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseVmVBActivity<VM : ViewModel, VB : ViewBinding> : BaseVBActivity<VB>() {

    protected val mViewModel: VM by lazy {
        val types = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        val clazz = types[0] as Class<VM>
        val factory= object : ViewModelProvider.Factory {
            override fun <VM: ViewModel?> create(modelClass: Class<VM>): VM {
                return clazz.newInstance() as VM
            }
        }
        ViewModelProvider(this,factory).get(clazz)
    }
}