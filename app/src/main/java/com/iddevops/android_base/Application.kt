package com.iddevops.android_base

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.multidex.MultiDex
import com.iddevops.android_base.utils.bases.BaseApplication
import org.koin.dsl.module

class Application : BaseApplication() {

    override fun defineKoinModules() = arrayListOf(
        module {  }
    )

    override fun defineNotificationChannel(): List<NotificationChannel> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            listOf(
                NotificationChannel(
                    BuildConfig.NOTIFICATION_CHANNEL,
                    BuildConfig.NOTIFICATION_CHANNEL,
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        } else {
            listOf()
        }

    // Scopes
    override fun initApplication() {

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}