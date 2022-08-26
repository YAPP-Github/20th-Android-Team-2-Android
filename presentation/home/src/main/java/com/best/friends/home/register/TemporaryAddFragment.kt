package com.best.friends.home.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.best.friends.home.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * 절약 추가 화면 Activity로 이동하기 위해 제작한 Fragment
 */
@AndroidEntryPoint
class TemporaryAddFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_temporary_add, container, false)
    }

    companion object {

        fun newInstance(): TemporaryAddFragment {
            return TemporaryAddFragment()
        }
    }
}
