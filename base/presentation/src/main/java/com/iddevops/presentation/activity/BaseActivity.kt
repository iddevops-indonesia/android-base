package com.iddevops.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.iddevops.presentation.viewbinding.getBinding

open class BaseActivity<VB: ViewBinding>: FragmentActivity() {
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getBinding()
        setContentView(binding.root)

        initData()
        initAction()
        initUi()
        lifecycleScope.launchWhenResumed {
            initObserver()
        }
    }

    open fun initData(){}
    open fun initAction(){}
    open fun initUi(){}
    open suspend fun initObserver(){}
}