package com.txd.compose

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Application 基类
 *
 * @ClassName:      BaseApplication
 * @CreateDate:     2022-09-01
 */
open class BaseApplication : Application() {

    companion object {
        @JvmStatic
        @SuppressLint("StaticFieldLeak")
        lateinit var context: BaseApplication
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        context = this
    }
}