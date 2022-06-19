package com.yapp.android2.record

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.best.friends.core.BaseFragment
import com.yapp.android2.record.databinding.FragmentRecordBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthScrollListener
import com.yapp.android2.record.adapter.RecordAdapter
import com.yapp.android2.record.view.*
import com.yapp.android2.record.view.DayBind
import com.yapp.android2.record.view.firstDayOfWeek
import com.yapp.android2.record.view.firstMonth
import com.yapp.android2.record.view.lastMonth
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

    private fun FragmentRecordBinding.viewInit() {
//        viewPager.adapter = recordAdapter

    }

    private fun FragmentRecordBinding.setListener() {
        ivNextMonth.setOnClickListener {  }

        ivPrevMonth.setOnClickListener {  }

        calendar.monthScrollListener = object : MonthScrollListener {
            override fun invoke(calendar: CalendarMonth) {
                calendarTitle.text = getString(R.string.calendar_title, calendar.yearMonth.year, calendar.yearMonth.month.value)
            }

        }
    }

    private fun RecordViewModel.setObserve() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            items.collect {
//                recordAdapter.submitList(it)
//            }
//        }

    }
}
