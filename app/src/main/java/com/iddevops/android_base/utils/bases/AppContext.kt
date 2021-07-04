package com.iddevops.android_base.utils.bases

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object AppContext {

    private var mContext: Context? = null

    /**
     * Init context
     * @param context Application Context
     */
    private fun init(context: Context) {
        mContext = context
    }

    /**
     * Check if context is null or not
     * @return Application Context
     */
    private fun getContext() = mContext ?: throw IllegalStateException("call init first")

    @JvmStatic
    fun setBaseContext(context: Context) {
        init(context)
    }

    @JvmStatic
    fun get(): Context {
        return getContext()
    }
}