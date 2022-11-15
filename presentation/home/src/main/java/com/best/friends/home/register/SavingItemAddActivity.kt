package com.best.friends.home.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.InputFilter
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseActivity
import com.best.friends.core.extensions.Empty
import com.best.friends.core.extensions.showToast
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.home.R
import com.best.friends.home.databinding.ActivitySavingItemAddBinding
import com.best.friends.home.register.SavingItemAddViewModel.Action.ItemAdded
import com.best.friends.navigator.HomeNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * 절약 추가 화면 Activity
 */
@AndroidEntryPoint
class SavingItemAddActivity :
    BaseActivity<ActivitySavingItemAddBinding>(R.layout.activity_saving_item_add) {

    private val viewModel by viewModels<SavingItemAddViewModel>()

    @Inject
    lateinit var homeNavigator: HomeNavigator

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            startActivity(homeNavigator.intent(this@SavingItemAddActivity))
            finish()
        }
    }


    private val dayOfWeekCheckBoxIdMap = mapOf(
        R.id.cb_monday to MONDAY,
        R.id.cb_tuesday to TUESDAY,
        R.id.cb_wednesday to WEDNESDAY,
        R.id.cb_thursday to THURSDAY,
        R.id.cb_friday to FRIDAY,
        R.id.cb_saturday to SATURDAY,
        R.id.cb_sunday to SUNDAY
    )

    private val checkListener by lazy {
        CompoundButton.OnCheckedChangeListener { view, isChecked ->
            val dayOfWeek = requireNotNull(dayOfWeekCheckBoxIdMap[view.id]) {
                "요일을 선택하지 않았습니다"
            }

            if(!viewModel.isAvailDayOfWeek(dayOfWeek)) {
                view.isChecked = false
                showToast(getString(R.string.saving_items_impossible_day_of_week))
            } else if (isChecked) {
                viewModel.addSavingDayOfWeek(dayOfWeek)
            } else {
                viewModel.deleteSavingDayOfWeek(dayOfWeek)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        this.onBackPressedDispatcher.addCallback(this, callback)
        setToolbar()
        initView()
        observe()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun setToolbar() {
        binding.ivBack.setOnSingleClickListener {
            callback.handleOnBackPressed()
        }

        binding.tvToolbarTitle.text = getString(R.string.saving_item_activity_toolbar_title)
    }

    private fun initView() {
        binding.ivClearContent.setOnSingleClickListener {
            viewModel.setContentText(String.Empty)
            val editText = binding.etItemContent
            editText.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, 0)
        }

        binding.cbMonday.setOnCheckedChangeListener(checkListener)
        binding.cbTuesday.setOnCheckedChangeListener(checkListener)
        binding.cbWednesday.setOnCheckedChangeListener(checkListener)
        binding.cbThursday.setOnCheckedChangeListener(checkListener)
        binding.cbFriday.setOnCheckedChangeListener(checkListener)
        binding.cbSaturday.setOnCheckedChangeListener(checkListener)
        binding.cbSunday.setOnCheckedChangeListener(checkListener)
    }

    private fun observe() {
        viewModel.error
            .onEach { errorMessage -> showToast(errorMessage) }
            .launchIn(lifecycleScope)

        viewModel.action
            .onEach { action ->
                when (action) {
                    ItemAdded -> {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }
            }
            .launchIn(lifecycleScope)

        binding.etItemContent.setOnFocusChangeListener { _, hasFocus ->
            val editText = binding.etItemContent
            val whiteSpaceFilter = InputFilter { source, start, end, _, _, _ ->
                if (binding.etItemContent.text.isBlank()) {
                    for (i in start until end) {
                        if (Character.isWhitespace(source[i])) {
                            return@InputFilter ""
                        }
                    }
                }
                null
            }

            if (hasFocus) {
                editText.filters = arrayOf(whiteSpaceFilter)
            }
        }
    }

    companion object {

        fun intent(context: Context): Intent {
            return Intent(context, SavingItemAddActivity::class.java)
        }

        const val MONDAY = 0
        const val TUESDAY = 1
        const val WEDNESDAY = 2
        const val THURSDAY = 3
        const val FRIDAY = 4
        const val SATURDAY = 5
        const val SUNDAY = 6
    }
}
