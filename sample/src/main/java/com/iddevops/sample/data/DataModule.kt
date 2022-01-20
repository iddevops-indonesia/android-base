package com.iddevops.sample.data

import android.content.Context
import com.iddevops.data.retrofit.createApiProvider
import com.iddevops.data.retrofit.createOkHttpService
import com.iddevops.sample.data.activity.ActivityData
import com.iddevops.sample.data.activity.web.ActivityApi
import com.iddevops.sample.domain.repo.ActivityRepository
import com.iddevops.sample.core.interceptor.DefaultInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module

private const val API_URL = "https://jsonplaceholder.typicode.com/"

val dataModule: (context: Context) -> Module = { context ->
    module {
        single {
            createOkHttpService(
                context,
                DefaultInterceptor()
            )
        }

        factory {
            createApiProvider(
                API_URL,
                get(),
                ActivityApi::class.java
            )
        }

        factory<ActivityRepository> { ActivityData(get()) }
    }
}