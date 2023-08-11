package com.worldpeak.chnsmilead.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.worldpeak.chnsmilead.util.StatusBarUtil

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setTranslucent(this)
        setContentView(getLayoutId())
        initView()
        loadData()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun loadData()
}