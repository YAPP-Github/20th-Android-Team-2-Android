package com.best.friends.home.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseFragment
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.core.ui.showToast
import com.best.friends.core.ui.visibleOrGone
import com.best.friends.home.R
import com.best.friends.home.databinding.FragmentHomeBinding
import com.best.friends.home.dialog.DatePickerWithTodayButtonDialog
import com.best.friends.home.home.HomeViewModel.Action.CalendarClick
import com.best.friends.home.register.SavingItemAddActivity
import com.best.friends.home.update.SavingItemUpdateActivity
import com.best.friends.navigator.NotificationNavigator
import com.best.friends.navigator.SettingNavigator
import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.key.PRODUCT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.NumberFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * 홈탭 화면 Fragment
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var settingNavigator: SettingNavigator

    @Inject
    lateinit var notificationNavigator: NotificationNavigator

    private val addResultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getProductsToday()
        }
    }

    private val adapter by lazy {
        SavingsListAdapter(
            onItemClick = { product -> startSavingUpdateActivity(product) },
            onAddClick = { startSavingAddActivity() },
            onItemChecked = { product ->
                viewModel.checkSavingItem(product)

                setFragmentResult(PRODUCT, bundleOf(product.name.orEmpty() to product))
            }
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
            startActivity(notificationNavigator.intent(requireContext()))
        }

        binding.ivSettings.setOnSingleClickListener {
            startActivity(settingNavigator.intent(requireContext()))
        }

        binding.emptyView.tvSavingItemsAdd.setOnSingleClickListener {
            startSavingAddActivity()
        }

        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = adapter
    }

    private fun observe() {
        viewModel.state
            .onEach { state ->
                val (_, day, products) = state
                binding.tvDay.text = String.format("%d월 %d일", day.monthValue, day.dayOfMonth)

                if (state.isInitialized) {
                    binding.recyclerView.visibleOrGone(products.isNotEmpty())
                    binding.emptyView.root.visibleOrGone(!state.isPastDate && products.isEmpty())
                    binding.tvEmptyTitleWhenPastDate
                        .visibleOrGone(state.isPastDate && products.isEmpty())

                    if (products.isNotEmpty()) {
                        adapter.submit(products)
                    }

                    val descriptionText = if (state.priceSum == 0) {
                        getString(R.string.saving_items_price_sum_default)
                    } else {
                        String.format(
                            getString(R.string.saving_items_price_sum_format),
                            NumberFormat.getInstance().format(state.priceSum)
                        )
                    }

                    binding.tvDescription.text = descriptionText
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.action.onEach { action ->
            when (action) {
                is CalendarClick -> showDatePicker(action.currentDay)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.error
            .onEach { errorMessage -> showToast(errorMessage) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showDatePicker(zonedDateTime: ZonedDateTime) {
        DatePickerWithTodayButtonDialog.show(
            fragmentManager = childFragmentManager,
            lifecycleOwner = viewLifecycleOwner,
            year = zonedDateTime.year,
            month = zonedDateTime.month.value - 1,
            dayOfMonth = zonedDateTime.dayOfMonth,
            listener = { year, month, dayOfMonth ->
                val new = ZonedDateTime.of(
                    year,
                    month + 1,
                    dayOfMonth,
                    0, 0, 0, 0, ZoneId.systemDefault()
                )

                viewModel.getProductsSelectDay(new)
            }
        )
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
