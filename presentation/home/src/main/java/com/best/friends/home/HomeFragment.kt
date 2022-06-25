package com.best.friends.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseFragment
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.core.ui.visibleOrGone
import com.best.friends.home.HomeViewModel.State
import com.best.friends.home.databinding.FragmentHomeBinding
import com.best.friends.navigator.SettingNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * 홈탭 화면 Fragment
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    @Inject
    lateinit var settingNavigator: SettingNavigator

    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        binding.ivNotifications.setOnSingleClickListener {
            // TODO 알림 화면으로 이동
        }

        binding.ivSettings.setOnSingleClickListener {
            startActivity(settingNavigator.intent(requireContext()))
        }

        binding.emptyView.tvSavingItemsAdd.setOnSingleClickListener {
            // TODO 절약 추가 화면으로 이동
        }
    }

    private fun observe() {
        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .filter { it != State.Default }
            .onEach { state ->
                val (day, products) = state
                binding.tvDay.text = String.format("%d월 %d일", day.monthValue, day.dayOfMonth)
                binding.emptyView.root.visibleOrGone(products.isEmpty())
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
