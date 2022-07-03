package com.best.friends.home.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.home.databinding.FragmentDialogHorizontalButtonsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HorizontalButtonsDialogFragment private constructor() : DialogFragment() {

    private lateinit var binding: FragmentDialogHorizontalButtonsBinding
    private val title by lazy { arguments?.getString(TITLE).orEmpty() }
    private val description by lazy { arguments?.getString(DESCRIPTION).orEmpty() }
    private val negativeButtonName by lazy { arguments?.getString(NEGATIVE_BUTTON_NAME).orEmpty() }
    private val positiveButtonName by lazy { arguments?.getString(POSITIVE_BUTTON_NAME).orEmpty() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogHorizontalButtonsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = title
        binding.tvDescription.text = description
        binding.tvNegative.text = negativeButtonName
        binding.tvPositive.text = positiveButtonName

        binding.tvNegative.setOnSingleClickListener {
            setFragmentResult(RESULT, bundleOf(RESULT_NEGATIVE_ACTION to true))
            dismiss()
        }
        binding.tvPositive.setOnSingleClickListener {
            setFragmentResult(RESULT, bundleOf(RESULT_POSITIVE_ACTION to true))
            dismiss()
        }
    }

    companion object {
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val NEGATIVE_BUTTON_NAME = "negative_button_name"
        private const val POSITIVE_BUTTON_NAME = "positive_button_name"
        private const val TAG = "HorizontalButtonsDialogFragment"

        private const val RESULT = "result"
        private const val RESULT_NEGATIVE_ACTION = "result_negative_action"
        private const val RESULT_POSITIVE_ACTION = "result_positive_action"

        fun newInstance(
            title: String,
            description: String,
            negativeButtonName: String,
            negativeAction: () -> Unit = {},
            positiveButtonName: String,
            positiveAction: () -> Unit = {},
        ): HorizontalButtonsDialogFragment {
            return HorizontalButtonsDialogFragment().apply {
                arguments = bundleOf(
                    TITLE to title,
                    DESCRIPTION to description,
                    NEGATIVE_BUTTON_NAME to negativeButtonName,
                    POSITIVE_BUTTON_NAME to positiveButtonName
                )
                setFragmentResultListener(RESULT) { _, bundle ->
                    when {
                        bundle.getBoolean(RESULT_NEGATIVE_ACTION, false) -> {
                            negativeAction.invoke()
                        }
                        bundle.getBoolean(RESULT_POSITIVE_ACTION, false) -> {
                            positiveAction.invoke()
                        }
                    }
                }
            }
        }

        fun HorizontalButtonsDialogFragment.show(fragmentManager: FragmentManager) {
            fragmentManager.executePendingTransactions()
            val fragment = fragmentManager.findFragmentByTag(TAG)
            if (fragment == null && !isAdded) {
                show(fragmentManager, TAG)
            }
        }
    }
}
