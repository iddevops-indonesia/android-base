package com.iddevops.utils.interceptor

import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.Response
import kotlin.getOrDefault as getOr

class DefaultInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
        val url = req.url().newBuilder().build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}
