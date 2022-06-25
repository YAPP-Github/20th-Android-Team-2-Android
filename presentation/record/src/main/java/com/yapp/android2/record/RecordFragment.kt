package com.yapp.android2.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseFragment
import com.best.friends.core.setOnSingleClickListener
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthScrollListener
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import com.yapp.android2.record.adapter.RecordAdapter
import com.yapp.android2.record.databinding.FragmentRecordBinding
import com.yapp.android2.record.view.setOffsetTransformer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecordFragment : BaseFragment<FragmentRecordBinding, RecordViewModel>(R.layout.fragment_record) {

    override val viewModel: RecordViewModel by viewModels()
    private val recordAdapter = RecordAdapter()

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

        binding.viewInit()
        binding.setListener()
        viewModel.setObserve()
        viewModel.fetchRecords()

        onBind {
            viewModel = this@RecordFragment.viewModel
            executePendingBindings()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchRecords()
    }

    private fun FragmentRecordBinding.viewInit() {
        viewPager.adapter = recordAdapter
        viewPager.offscreenPageLimit = 3
        viewPager.setOffsetTransformer()
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
        viewLifecycleOwner.lifecycleScope.launch {
            items.collect {
                recordAdapter.submitList(it)
            }
        }
    }

    companion object {

        fun newInstance(): RecordFragment {
            return RecordFragment()
        }
    }
}
