package com.iddevops.sample.core.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class DefaultInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
        val url = req.url().newBuilder().build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}
