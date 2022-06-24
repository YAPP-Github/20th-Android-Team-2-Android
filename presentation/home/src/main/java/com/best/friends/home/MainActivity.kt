package com.best.friends.home

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.best.friends.core.BaseActivity
import com.best.friends.core.setOnSingleClickListener
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
        initView()
        setupViewPager()
    }

    private fun initView() {
        binding.ivNotifications.setOnSingleClickListener {
            // TODO 알림 화면으로 이동
        }

        binding.ivSettings.setOnSingleClickListener {
            // TODO 설정 화면으로 이동
        }
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = fragmentStateAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabBinding = LayoutCustomTabBinding.inflate(layoutInflater)
            tabBinding.ivIcon.setImageResource(designR.drawable.icon_home_inactive)
            tabBinding.tvTitle.text = fragmentStateAdapter.getTabTitle(position)
            tab.customView = tabBinding.root
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : AbstractTabSelectedListener() {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.customView?.let { view ->
                    val position: Int = tab.position
                    val tabBinding = LayoutCustomTabBinding.bind(view)
                    val textColor = ContextCompat.getColor(view.context, designR.color.gray4)
                    val drawable = fragmentStateAdapter.getTabSelectedResource(position)

                    tabBinding.tvTitle.setTextColor(textColor)
                    tabBinding.ivIcon.setImageDrawable(drawable)

                    view.findViewById<TextView>(R.id.tv_title).setTextColor(textColor)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.customView?.let { view ->
                    val position: Int = tab.position
                    val tabBinding = LayoutCustomTabBinding.bind(view)
                    val textColor = ContextCompat.getColor(view.context, designR.color.gray4)
                    val drawable = fragmentStateAdapter.getTabUnSelectedResource(position)

                    tabBinding.tvTitle.setTextColor(textColor)
                    tabBinding.ivIcon.setImageDrawable(drawable)

                    view.findViewById<TextView>(R.id.tv_title).setTextColor(textColor)
                }
            }
        })
    }
}
