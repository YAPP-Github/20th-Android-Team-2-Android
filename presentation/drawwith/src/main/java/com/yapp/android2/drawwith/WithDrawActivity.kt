package com.yapp.android2.drawwith

import android.os.Bundle
import com.best.friends.core.BaseActivity
import com.best.friends.core.setOnSingleClickListener
import com.yapp.android2.drawwith.databinding.ActivityWithDrawBinding

class WithDrawActivity : BaseActivity<ActivityWithDrawBinding>(R.layout.activity_with_draw) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivBack.setOnSingleClickListener { finish() }
        
    }
}
