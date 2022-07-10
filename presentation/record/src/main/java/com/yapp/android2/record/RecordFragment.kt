package com.yapp.android2.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.best.friends.core.BaseFragment
import com.best.friends.core.setOnSingleClickListener
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthScrollListener
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import com.yapp.android2.record.adapter.RecordAdapter
import com.yapp.android2.record.databinding.FragmentRecordBinding
import com.yapp.android2.record.view.setOffsetTransformer
import com.yapp.android2.record.view.currentMonth
import com.yapp.android2.record.view.firstDayOfWeek
import com.yapp.android2.record.view.DayBind
import com.yapp.android2.record.view.firstMonth
import com.yapp.android2.record.view.lastMonth
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RecordFragment : BaseFragment<FragmentRecordBinding, RecordViewModel>(R.layout.fragment_record) {

    override val viewModel: RecordViewModel by activityViewModels()

    private val recordAdapter = RecordAdapter()

    private val pagerListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)

            val items = recordAdapter.currentList[position].recordDates

            binding.calendar.dayBinder = DayBind.newInstance(items)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchRecords()
        binding.viewInit()
        binding.setListener()
        viewModel.setObserve()

        onBind {
            viewModel = this@RecordFragment.viewModel
            executePendingBindings()
        }
    }

    override fun onDestroyView() {
        binding.viewPager.unregisterOnPageChangeCallback(pagerListener)
        super.onDestroyView()
    }

    private fun FragmentRecordBinding.viewInit() {
        viewPager.adapter = recordAdapter
        viewPager.offscreenPageLimit = 3

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer(viewPager.setOffsetTransformer())
        }

        viewPager.setPageTransformer(compositePageTransformer)
        this.viewPager.registerOnPageChangeCallback(pagerListener)

        this.calendar.dayBinder = DayBind.newInstance()
        with(binding) {

            viewPager.setPageTransformer(compositePageTransformer)
            viewPager.registerOnPageChangeCallback(pagerListener)

            calendar.dayBinder = DayBind.newInstance()
            calendar.setup(firstMonth, lastMonth, firstDayOfWeek)
            calendar.scrollToMonth(currentMonth)
        }
    }

    private fun FragmentRecordBinding.setListener() {
        ivNextMonth.setOnSingleClickListener {
            calendar.findFirstVisibleMonth()?.let {
                calendar.smoothScrollToMonth(it.yearMonth.next)
            }
        }

        ivPrevMonth.setOnSingleClickListener {
            calendar.findFirstVisibleMonth()?.let {
                calendar.smoothScrollToMonth(it.yearMonth.previous)
            }
        }

        calendar.monthScrollListener = object : MonthScrollListener {
            override fun invoke(calendar: CalendarMonth) {
                calendarTitle.text = getString(
                    R.string.calendar_title,
                    calendar.yearMonth.year,
                    calendar.yearMonth.month.value
                )
            }
        }
    }

    private fun RecordViewModel.setObserve() {
        items
            .filter { it.isNotEmpty() }
            .onEach {
                val distinctList = it.distinctBy { item -> item.record.name }
                recordAdapter.submitList(distinctList)

                val recordDates = distinctList[binding.viewPager.currentItem].recordDates

                binding.calendar.dayBinder = DayBind.newInstance(recordDates)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        action
            .onEach { fetchRecords() }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {

        fun newInstance(): RecordFragment {
            return RecordFragment()
        }
    }
}
