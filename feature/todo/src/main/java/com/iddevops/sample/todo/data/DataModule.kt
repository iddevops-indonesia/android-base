package com.iddevops.sample.todo.data

import android.content.Context
import com.iddevops.data.retrofit.createApiProvider
import com.iddevops.data.retrofit.createOkHttpService
import com.iddevops.sample.todo.data.activity.TodoListData
import com.iddevops.sample.todo.data.activity.web.TodoListApi
import com.iddevops.sample.todo.domain.repo.TodoListRepository
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
                TodoListApi::class.java
            )
        }

        factory<TodoListRepository> { TodoListData(get()) }
    }
}