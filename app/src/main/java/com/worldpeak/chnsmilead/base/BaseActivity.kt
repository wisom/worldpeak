package com.worldpeak.chnsmilead.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.worldpeak.chnsmilead.util.StatusBarUtil
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTranslucent(this)
        setContentLayout()
        //判断是否需要注册EventBus
        if (this.javaClass.isAnnotationPresent(UseEventBus::class.java)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.javaClass.isAnnotationPresent(UseEventBus::class.java)) {
            EventBus.getDefault().unregister(this)
        }
    }

    open fun setContentLayout() {
        setContentView(getLayoutId())
    }

    abstract fun getLayoutId(): Int

    open fun initView() {}

    abstract fun loadData()
}
