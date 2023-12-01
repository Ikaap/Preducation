package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.AboutFragment
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.CurriculcumFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            AboutFragment()
        } else {
            CurriculcumFragment()
        }
    }
}
