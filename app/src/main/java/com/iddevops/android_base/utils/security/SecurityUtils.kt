package com.iddevops.android_base.utils.security

import android.content.Context
import androidx.annotation.IntDef
import com.nekolaboratory.EmulatorDetector
import com.scottyab.rootbeer.RootBeer

const val EMULATOR = 0
const val ROOTED = 2
const val SAFE = 1

@IntDef(EMULATOR, ROOTED, SAFE)
@Retention(AnnotationRetention.SOURCE)
annotation class SecurityCheck

/**
 * Method to check device integrity
 * @return is the device an emulator, rooted, or safe
 */
@SecurityCheck
fun Context.securityCheck(): Int {
    return when {
        isEmulator(this) -> EMULATOR
        isRooted(this) -> ROOTED
        else -> SAFE
    }
}

/**
 * @param ctx context
 * @return is device an emulator
 */
private fun isEmulator(ctx: Context): Boolean {
    return try {
        EmulatorDetector.isEmulator(ctx)
    } catch (t: Throwable) {
        false
    }
}

/**
 * @param ctx context
 * @return is device rooted
 */
private fun isRooted(ctx: Context): Boolean {
    val rootBeer = RootBeer(ctx)
    return if (rootBeer.isRooted)
        !rootBeer.isRootedWithBusyBoxCheck
    else
        false
}