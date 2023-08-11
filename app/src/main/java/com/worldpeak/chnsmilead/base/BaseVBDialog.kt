package com.worldpeak.chnsmilead.base

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

open class BaseVBDialog<VB : ViewBinding>(activity: Activity, themeResId: Int) :
    Dialog(activity, themeResId) {

    protected lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

}