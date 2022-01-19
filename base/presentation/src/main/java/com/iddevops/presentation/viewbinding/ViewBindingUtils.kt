package com.iddevops.presentation.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.iddevops.presentation.fragment.BaseFragment
import java.lang.reflect.ParameterizedType

internal fun <V : ViewBinding> Class<*>.getBinding(layoutInflater: LayoutInflater): V {
    return try {
        @Suppress("UNCHECKED_CAST")
        getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as V
    } catch (t: Throwable) {
        throw RuntimeException("The ViewBinding inflate function has been changed.", t)
    }
}

internal fun Class<*>.checkMethod(): Boolean {
    return try {
        getMethod("inflate", LayoutInflater::class.java)
        true
    } catch (ex: Exception) {
        false
    }
}

inline fun <reified V : ViewBinding> ViewGroup.toBinding(): V {
    return V::class.java.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    ).invoke(null, LayoutInflater.from(context), this, false) as V
}

internal fun Any.findClass(): Class<*> {
    var javaClass: Class<*> = this.javaClass
    var result: Class<*>? = null
    while (result == null || !result.checkMethod()) {
        result = (javaClass.genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments?.firstOrNull {
                if (it is Class<*>) {
                    it.checkMethod()
                } else {
                    false
                }
            } as? Class<*>
        javaClass = javaClass.superclass
    }
    return result
}

//internal fun <V : ViewBinding> DevActivity<V>.getBinding(): V {
//    return findClass().getBinding(layoutInflater)
//}

internal fun <V : ViewBinding> BaseFragment<V>.getBinding(): V {
    return findClass().getBinding(layoutInflater)
}