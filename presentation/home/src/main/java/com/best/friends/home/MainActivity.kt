package com.best.friends.home

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.best.friends.core.BaseActivity
import com.best.friends.core.extensions.showToast
import com.best.friends.home.databinding.ActivityMainBinding
import com.best.friends.home.databinding.LayoutCustomTabBinding
import com.best.friends.home.register.SavingItemAddActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yapp.android2.deeplink.DeepLinkPrefixSpec
import dagger.hilt.android.AndroidEntryPoint
import com.best.friend.design.R as designR

/**
 * 홈탭, 기록탭 TabLayout 이 있는 메인화면 Activity
 */
@DeepLinkPrefixSpec(
    value = [
        "start"
    ]
)
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()
    private val fragmentStateAdapter = FragmentStateAdapter(this)

    private var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewPager()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime >= 2000) {
            backPressedTime = System.currentTimeMillis()
            showToast(getString(R.string.back_pressed_in_cool_time))
        } else {
            super.onBackPressed()
        }
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

        // 탭 사이 간격을 20으로 설정
        for (index in 0 until binding.tabLayout.tabCount) {
            val tab = (binding.tabLayout.getChildAt(0) as ViewGroup).getChildAt(index)
            tab.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                when (index) {
                    0 -> setMargins(0, 0, 10, 0)
                    1 -> setMargins(10, 0, 10, 0)
                    else -> setMargins(10, 0, 0, 0)
                }
            }
            tab.requestLayout()
        }

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

        // 절약 추가 tab 클릭 시 SavingItemAddActivity로 이동
        if(tab.position == 1) {
            val intent = Intent(this, SavingItemAddActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setTabUnSelected(tab: TabLayout.Tab) {
        tab.customView?.let { view ->
            val textColor = ContextCompat.getColor(view.context, designR.color.gray3)
            val drawable = fragmentStateAdapter.getTabUnSelectedResource(tab.position)
            val tvTitle = view.findViewById<TextView>(R.id.tv_title)
            val ivIcon = view.findViewById<ImageView>(R.id.iv_icon)

            tvTitle.setTextColor(textColor)
            ivIcon.setImageDrawable(drawable)
        }
    }
}
