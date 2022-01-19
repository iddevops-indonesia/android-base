package com.iddevops.utils.worker

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread

@MainThread
fun <T> runInMainThread(task: () -> T): T {
    return task.invoke()
}

fun wait(delay: Long = 1000, task: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        task.invoke()
    }, delay)
}