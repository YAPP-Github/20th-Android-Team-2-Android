package com.best.friends.home.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.InputFilter
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseActivity
import com.best.friends.core.ui.showToast
import com.best.friends.home.R
import com.best.friends.home.databinding.ActivitySavingItemAddBinding
import com.best.friends.home.register.SavingItemAddViewModel.Action.ItemAdded
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DecimalFormat


/**
 * 절약 추가 화면 Activity
 */
@AndroidEntryPoint
class SavingItemAddActivity :
    BaseActivity<ActivitySavingItemAddBinding>(R.layout.activity_saving_item_add) {

    private val viewModel by viewModels<SavingItemAddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        setToolbar()
        initView()
        observe()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
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
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(com.best.friend.design.R.drawable.icon_chevron_left)
        supportActionBar?.title = getString(R.string.saving_item_activity_toolbar_title)
    }

    private fun initView() {
        val filter = InputFilter { source, start, end, _, _, _ ->
            for (i in start until end) {
                if (Character.isWhitespace(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }

        binding.etItemContent.filters = arrayOf(filter)
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

        binding.etItemPrice.setOnFocusChangeListener { view, hasFocus ->
            val editText = binding.etItemPrice
            val price = viewModel.price.value
            if (price.isBlank()) {
                return@setOnFocusChangeListener
            }

            if (hasFocus) {
                editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(6))
                val onlyNumber = price
                    .replace(",", "")
                    .replace("원", "")
                editText.setText("")
                editText.append(onlyNumber)
            } else {
                editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
                val formatText = DecimalFormat("###,###").format(price.toInt())
                val wonText = String.format("%s원", formatText)
                viewModel.setPriceText(wonText)
            }
        }
    }

    companion object {

        fun intent(context: Context): Intent {
            return Intent(context, SavingItemAddActivity::class.java)
        }
    }
}
