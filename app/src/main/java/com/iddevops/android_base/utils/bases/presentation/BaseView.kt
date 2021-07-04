package com.iddevops.android_base.utils.bases.presentation

import androidx.appcompat.widget.Toolbar

interface BaseView {
    /**
     * Method to set toolbar, can be called from DevActivity or DevFragment
     * @param toolbar Toolbar that defined in XML layout, nullable
     * @param title Title for toolbar, nullable
     * @param isChild Display back button it toolbar?
     */
    fun setupToolbar(
        toolbar: Toolbar? = null,
        title: String? = null,
        isChild: Boolean,
        menu: Int? = null,
        onMenuListener: ((Int) -> Boolean)? = null
    )

    /**
     * Method to finish Activity, so you can finish activity from fragment
     */
    fun finishActivity()
}