package com.iddevops.android_base.utils.bases

import android.app.Activity
import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.iddevops.android_base.BuildConfig
import com.iddevops.android_base.R
import com.iddevops.android_base.utils.security.EMULATOR
import com.iddevops.android_base.utils.security.ROOTED
import com.iddevops.android_base.utils.security.securityCheck
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

abstract class BaseApplication : Application() {

    /**
     * Define series of action when application initialized
     */
    abstract fun initApplication()

    /**
     * Method to declare list of Koin Modules within apps
     * @return list of koin modules
     */
    abstract fun defineKoinModules(): List<Module>

    /**
     * Method to define notification channels
     * @return list of notification channels
     */
    abstract fun defineNotificationChannel(): List<NotificationChannel>

    /**
     * Method to declare action on global state
     * For now, it's used to :
     * 1. start Koin Injection
     * 2. Timber Plant Log
     * 3. Setting App Context
     */
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(defineKoinModules())
            androidLogger(Level.INFO)
        }

        /**
         * Kill insecure app
         */
        killInsecureApp()

        initApplication()

        createNotificationChannel()
        /// Timber.plant(Timber.DebugTree())

        /**
         * First temporary context
         */
        AppContext.setBaseContext(applicationContext)
        setActivityContext()
    }

    /**
     * Method to create notification from channels
     */
    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        defineNotificationChannel().forEach {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                    it.apply {
                        enableLights(true)
                        setShowBadge(true)
                        setSound(
                            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                            null
                        )
                        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    }
                )
            }
        }
    }

    private fun killInsecureApp() {

        when (securityCheck()) {
            ROOTED -> {
                if (BuildConfig.ALLOW_ROOT) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.error_running_in_a_rooted_device),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
            }
            EMULATOR -> {
                if (BuildConfig.ALLOW_EMULATOR) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.error_running_on_an_emulator),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
            }
            else -> return
        }

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

                /**
                 * Kill Application immediately when activity created
                 */
                Toast.makeText(
                    applicationContext,
                    getString(R.string.error_device_not_safe),
                    Toast.LENGTH_SHORT
                ).show()
                activity.finishAffinity()
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }

    private fun setActivityContext() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                AppContext.setBaseContext(activity)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }
}
