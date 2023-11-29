package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityDetailClassBinding
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.adapter.ViewPagerAdapter

class DetailClassActivity : AppCompatActivity() {

    private val binding: ActivityDetailClassBinding by lazy {
        ActivityDetailClassBinding.inflate(layoutInflater)
    }

    private val adapterViewPager: ViewPagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager, lifecycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setLayoutViewPager()
        setBottomSheet()
    }

    private fun setBottomSheet() {

    }

    private fun setLayoutViewPager() {
        with(binding) {
            tabLayout.addTab(tabLayout.newTab().setText("About"))
            tabLayout.addTab(tabLayout.newTab().setText("Curriculcum"))

            viewPager.adapter = adapterViewPager

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                @SuppressLint("UseCompatLoadingForDrawables")
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        viewPager.currentItem = tab.position
                        tab?.view?.background = getDrawable(R.drawable.selected_tab_background)
                    }
                }

                @SuppressLint("UseCompatLoadingForDrawables")
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.view?.background = getDrawable(R.drawable.unselected_tab_background)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }
    }
}
