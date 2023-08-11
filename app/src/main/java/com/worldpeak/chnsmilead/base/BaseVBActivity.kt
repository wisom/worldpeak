package com.worldpeak.chnsmilead.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.worldpeak.chnsmilead.R
import com.worldpeak.chnsmilead.view.CommonTitleBar
import java.lang.reflect.ParameterizedType

abstract class BaseVBActivity<VB : ViewBinding> : BaseActivity() {

    lateinit var mBinding: VB

    override fun setContentLayout() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val actualTypeArguments = type.actualTypeArguments
            val clazz =
                    (if (actualTypeArguments.size > 1) actualTypeArguments[1] else actualTypeArguments[0]) as Class<VB>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            mBinding = method.invoke(null, layoutInflater) as VB
            setContentView(mBinding.root)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tackleBack()
        initView()
        loadData()
    }

    private fun tackleBack() {
        mBinding.root.findViewById<CommonTitleBar>(R.id.titleBar)?.setBackClick { finish() }
    }

}