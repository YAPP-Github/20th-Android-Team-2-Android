package com.yapp.android2.drawwith

import android.os.Bundle
import androidx.activity.viewModels
import com.best.friends.core.BaseActivity
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.core.ui.showToast
import com.best.friends.navigator.LoginNavigator
import com.google.firebase.messaging.FirebaseMessaging
import com.yapp.android2.drawwith.databinding.ActivityWithDrawBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WithDrawActivity : BaseActivity<ActivityWithDrawBinding>(R.layout.activity_with_draw) {

    @Inject
    lateinit var loginNavigator: LoginNavigator

    @Inject
    lateinit var firebaseMessaging: FirebaseMessaging

    private val viewModel by viewModels<WithDrawViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvWithDrawDescription.text = getString(R.string.with_draw_description, viewModel.email)

        binding.ivBack.setOnSingleClickListener { finish() }

        binding.tvWithDraw.setOnSingleClickListener {
            viewModel.withDraw()
        }

        viewModel.finish.observe(this) {
            firebaseMessaging.deleteToken().addOnCompleteListener {
                if(it.isSuccessful) {
                    startActivity(loginNavigator.intent(this))
                    finishAffinity()
                } else {
                    this.showToast("알 수 없는 에러 발생")
                }
            }
        }
    }
}
