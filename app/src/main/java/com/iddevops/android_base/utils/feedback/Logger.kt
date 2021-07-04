package com.iddevops.android_base.utils.feedback

import android.util.Log

fun Any.logi(message: String) = Log.i(this.javaClass.simpleName, message)
fun Any.logd(message: String) = Log.d(this.javaClass.simpleName, message)
fun Any.logw(message: String) = Log.w(this.javaClass.simpleName, message)
fun Any.loge(message: String) = Log.e(this.javaClass.simpleName, message)
