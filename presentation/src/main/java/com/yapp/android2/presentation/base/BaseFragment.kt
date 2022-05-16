package com.yapp.android2.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB: ViewDataBinding, VM: BaseViewModel> : Fragment() {

    abstract val binding: VB

    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    inline internal fun onBind(body: VB.() -> Unit) {
        binding.run(body)
    }
}
