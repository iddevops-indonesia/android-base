package com.iddevops.data.retrofit

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun createOkHttpService(
    context: Context,
    interceptor: Interceptor? = null
): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient()
        .newBuilder()
        .addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
        .callTimeout(10, TimeUnit.SECONDS)

    interceptor?.let {
        okHttpClientBuilder.addInterceptor(it)
    }

    return okHttpClientBuilder.build()
}

fun <T> createApiProvider(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    service: Class<T>
): T{
    val retrofitProvider = createRetrofitProvider(baseUrl, okHttpClient)
    return retrofitProvider.create(service)
}

private fun createRetrofitProvider(
    baseUrl: String,
    okHttpClient: OkHttpClient,
): Retrofit {
    return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}