package com.iddevops.android_base.presentations.views

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Jika menggunakan xml inflater, gunakan kontrak ini
 * Pada kontrak ini, preparation di lakukan setelah draw()
 */

interface ViewBasicXml {
    val layoutResource: Int

    fun draw() {
        this as ViewGroup
        LayoutInflater.from(context).inflate(layoutResource, this, true)
    }
    fun setup()
    fun attributeSet()
}

/** ketiga fungsi di atas harus di jalankan pada init :
 *
 * init{
 *      draw()
 *      setup()
 *      attributeSet()
 * }
 *
 */
