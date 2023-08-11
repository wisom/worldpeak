package com.worldpeak.chnsmilead.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

abstract class BaseFragment : Fragment(){

    open var mContext: FragmentActivity? = null

    lateinit var mRootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        generateView(inflater, container)
        mContext = requireActivity()
        return mRootView
    }

    open fun generateView(inflater: LayoutInflater, container: ViewGroup?) {
        mRootView = inflater.inflate(getLayoutId(), container, false)
    }

    abstract fun getLayoutId(): Int
}