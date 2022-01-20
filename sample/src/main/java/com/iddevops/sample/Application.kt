package com.iddevops.sample

import android.app.Application
import com.iddevops.sample.todo.data.dataModule
import com.iddevops.sample.todo.domain.domainModule
import com.iddevops.sample.todo.presentation.presentationModule
import com.iddevops.sample.todo.todoModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class Application : Application() {

    private fun defineKoinModules() = ArrayList<Module>()
        .apply {
            addAll(todoModules(applicationContext))
        }


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(defineKoinModules())
            androidLogger(Level.INFO)
        }
    }
}