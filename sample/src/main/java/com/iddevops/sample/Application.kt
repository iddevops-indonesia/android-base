package com.iddevops.sample

import android.app.Application
import com.iddevops.sample.data.dataModule
import com.iddevops.sample.domain.domainModule
import com.iddevops.sample.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Application: Application() {
    
    private fun defineKoinModules() = arrayListOf(
        dataModule(applicationContext),
        domainModule,
        presentationModule
    )

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(defineKoinModules())
            androidLogger(Level.INFO)
        }
    }
}