package com.best.friends.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.best.friends.core.BaseActivity
import com.best.friends.home.databinding.ActivityMainBinding
import com.best.friends.home.databinding.LayoutCustomTabBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import com.best.friend.design.R as designR

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()
    private val fragmentStateAdapter = FragmentStateAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = fragmentStateAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabBinding = LayoutCustomTabBinding.inflate(layoutInflater)
            val icon = fragmentStateAdapter.getTabUnSelectedResource(position)

            tabBinding.ivIcon.setImageDrawable(icon)
            tabBinding.tvTitle.text = fragmentStateAdapter.getTabTitle(position)
            tab.customView = tabBinding.root
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : AbstractTabSelectedListener() {
            override fun onTabReselected(tab: TabLayout.Tab) {
                setTabSelected(tab)
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                setTabSelected(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                setTabUnSelected(tab)
            }
        })

        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
    }

    private fun setTabSelected(tab: TabLayout.Tab) {
        tab.customView?.let { view ->
            val textColor =
                ContextCompat.getColor(view.context, designR.color.color_primary)
            val drawable = fragmentStateAdapter.getTabSelectedResource(tab.position)
            val tvTitle = view.findViewById<TextView>(R.id.tv_title)
            val ivIcon = view.findViewById<ImageView>(R.id.iv_icon)

            tvTitle.setTextColor(textColor)
            ivIcon.setImageDrawable(drawable)
        }
    }

    private fun setTabUnSelected(tab: TabLayout.Tab) {
        tab.customView?.let { view ->
            val textColor = ContextCompat.getColor(view.context, designR.color.gray4)
            val drawable = fragmentStateAdapter.getTabUnSelectedResource(tab.position)
            val tvTitle = view.findViewById<TextView>(R.id.tv_title)
            val ivIcon = view.findViewById<ImageView>(R.id.iv_icon)

            tvTitle.setTextColor(textColor)
            ivIcon.setImageDrawable(drawable)
        }
    }
}
