package com.best.friends.home.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseFragment
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.core.ui.showToast
import com.best.friends.core.ui.visible
import com.best.friends.core.ui.visibleOrGone
import com.best.friends.home.R
import com.best.friends.home.databinding.FragmentHomeBinding
import com.best.friends.home.register.SavingItemAddActivity
import com.best.friends.home.update.SavingItemUpdateActivity
import com.best.friends.home.update.SavingItemUpdateModule
import com.yapp.android2.domain.entity.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * 홈탭 화면 Fragment
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel by viewModels<HomeViewModel>()

    private val addResultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getProductsToday()
        }
    }

    private val adapter by lazy {
        SavingsListAdapter(
            onItemClick = { product -> startSavingUpdateActivity(product) },
            onAddClick = { startSavingAddActivity() }
        )
    }

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

        binding.viewModel = viewModel
        initView()
        observe()
    }

    private fun initView() {
        binding.ivNotifications.setOnSingleClickListener {
            // TODO 알림 화면으로 이동
        }

        binding.ivSettings.setOnSingleClickListener {
            // TODO 설정 화면으로 이동
        }

        binding.emptyView.tvSavingItemsAdd.setOnSingleClickListener {
            startSavingAddActivity()
        }

        binding.recyclerView.adapter = adapter
    }

    private fun observe() {
        viewModel.state
            .onEach { state ->
                val (_, day, products) = state
                binding.tvDay.text = String.format("%d월 %d일", day.monthValue, day.dayOfMonth)

                if (state.isInitialized) {
                    binding.layout.visible()
                    binding.emptyView.root.visibleOrGone(products.isEmpty())

                    if (products.isNotEmpty()) {
                        adapter.submit(products)
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.error
            .onEach { errorMessage -> showToast(errorMessage) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun startSavingUpdateActivity(product: Product) {
        val intent = SavingItemUpdateActivity.intent(requireContext(), product)
        addResultLauncher.launch(intent)
    }

    private fun startSavingAddActivity() {
        val intent = SavingItemAddActivity.intent(requireContext())
        addResultLauncher.launch(intent)
    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
