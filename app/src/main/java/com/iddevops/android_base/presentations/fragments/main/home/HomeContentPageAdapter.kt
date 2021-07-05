package com.iddevops.android_base.presentations.fragments.main.home

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iddevops.android_base.presentations.fragments.main.home.three.Three
import com.iddevops.android_base.presentations.fragments.main.home.new.Two
import com.iddevops.android_base.presentations.fragments.main.home.one.One

class HomeContentPageAdapter(context: Context, fm: FragmentManager, lc: Lifecycle) :
    FragmentStateAdapter(fm,lc) {

    companion object {
        val PAGES = arrayListOf(
            One(),
            Two(),
            Three()
        )
    }

    override fun getItemCount() = PAGES.size

    override fun createFragment(position: Int) = PAGES[position]
}