package com.iddevops.sample.todo

import android.content.Context
import com.iddevops.sample.todo.data.dataModule
import com.iddevops.sample.todo.domain.domainModule
import com.iddevops.sample.todo.presentation.presentationModule
import org.koin.core.module.Module
import org.koin.dsl.module

val todoModules: (context: Context) -> List<Module> =
    {
        listOf(
            dataModule(it),
            domainModule,
            presentationModule
        )
    }