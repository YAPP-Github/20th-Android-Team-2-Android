package com.yapp.android2.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.best.friends.core.BaseFragment
import com.yapp.android2.record.databinding.FragmentRecordBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
        viewModel.fetchRecords()

        onBind {
            viewModel = this@RecordFragment.viewModel
            executePendingBindings()
        }

    }

    private fun FragmentRecordBinding.viewInit() {
        viewPager.adapter = recordAdapter

    }

    private fun RecordViewModel.setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            items.collect {
                recordAdapter.submitList(it)
            }
        }

    }
}
