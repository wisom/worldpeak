package com.worldpeak.chnsmilead.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseVBFragment<VB : ViewBinding> : BaseFragment() {

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    override fun generateView(inflater: LayoutInflater, container: ViewGroup?) {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val actualTypeArguments = type.actualTypeArguments
            val clazz =
                (if (actualTypeArguments.size > 1) actualTypeArguments[1] else actualTypeArguments[0]) as Class<VB>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            _binding = method.invoke(null, layoutInflater) as VB
        }
        mRootView = mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    open fun initView(){}

    abstract fun loadData()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}