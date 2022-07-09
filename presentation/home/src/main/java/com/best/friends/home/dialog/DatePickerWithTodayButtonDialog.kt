package com.best.friends.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.home.databinding.FragmentDialogDatePickerTodayButtonBinding
import java.time.ZonedDateTime

class DatePickerWithTodayButtonDialog private constructor() : DialogFragment() {

    fun interface DatePickerListener {
        fun onDateSet(year: Int, month: Int, dayOfMonth: Int)
    }

    private lateinit var binding: FragmentDialogDatePickerTodayButtonBinding
    private val year by lazy { arguments?.getInt(KEY_YEAR) ?: 0 }
    private val month by lazy { arguments?.getInt(KEY_MONTH) ?: 0 }
    private val dayOfMonth by lazy { arguments?.getInt(KEY_DAY_OF_MONTH) ?: 0 }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogDatePickerTodayButtonBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.datePicker.init(year, month, dayOfMonth, null)

        binding.tvConfirm.setOnSingleClickListener {
            setFragmentResult(RESULT, bundleOf(RESULT_POSITIVE_ACTION to true))
            dismiss()
        }

        binding.tvCancel.setOnSingleClickListener {
            dismiss()
        }

        // 오늘 클릭
        binding.tvToday.setOnSingleClickListener {
            setFragmentResult(RESULT, bundleOf(RESULT_TODAY_ACTION to true))
            dismiss()
        }
    }

    companion object {
        private const val TAG = "HorizontalButtonsDialogFragment"

        private const val KEY_YEAR = "year"
        private const val KEY_MONTH = "month"
        private const val KEY_DAY_OF_MONTH = "dayOfMonth"

        private const val RESULT = "result"
        private const val RESULT_POSITIVE_ACTION = "result_positive_action"
        private const val RESULT_TODAY_ACTION = "result_today_action"

        fun show(
            fragmentManager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            year: Int,
            month: Int, // 0~11
            dayOfMonth: Int,
            listener: DatePickerListener
        ) {
            return DatePickerWithTodayButtonDialog().apply {
                arguments = bundleOf(
                    KEY_YEAR to year,
                    KEY_MONTH to month,
                    KEY_DAY_OF_MONTH to dayOfMonth
                )
            }.also {
                fragmentManager.setFragmentResultListener(RESULT, lifecycleOwner) { _, bundle ->
                    when {
                        bundle.getBoolean(RESULT_POSITIVE_ACTION, false) -> {
                            listener.onDateSet(
                                year = bundle.getInt(KEY_YEAR),
                                month = bundle.getInt(KEY_MONTH),
                                dayOfMonth = bundle.getInt(KEY_DAY_OF_MONTH)
                            )
                        }
                        bundle.getBoolean(RESULT_TODAY_ACTION, false) -> {
                            val today = ZonedDateTime.now()
                            listener.onDateSet(
                                year = today.year,
                                month = today.monthValue,
                                dayOfMonth = today.dayOfMonth
                            )
                        }
                    }
                }
            }.show(fragmentManager, TAG)
        }
    }
}
