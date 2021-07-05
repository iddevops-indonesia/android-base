package com.iddevops.android_base.presentations.fragments.main.home

import com.iddevops.android_base.databinding.FragmentHomeBinding
import com.iddevops.android_base.utils.bases.presentation.BaseFragment

class Home : BaseFragment<FragmentHomeBinding>() {

    override fun getLayoutBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun initData() {

    }

    override fun initUI() {
        binding.vpContent.apply {
            adapter = HomeContentPageAdapter(context, childFragmentManager, lifecycle)
            offscreenPageLimit = 4
        }
    }

    override fun initAction() {

    }

    override fun initObserver() {

    }
}