package com.best.friends.home.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseActivity
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.core.ui.Empty
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
        binding.ivBack.setOnSingleClickListener {
            onBackPressed()
        }

        binding.tvToolbarTitle.text = getString(R.string.saving_item_activity_toolbar_title)
    }

    private fun initView() {
        binding.etItemPrice.inputType = TYPE_CLASS_NUMBER or TYPE_NUMBER_VARIATION_PASSWORD
        binding.etItemPrice.transformationMethod = null

        binding.ivClearContent.setOnSingleClickListener {
            viewModel.setContentText(String.Empty)
            val editText = binding.etItemContent
            editText.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, 0)
        }

        binding.ivClearPrice.setOnSingleClickListener {
            viewModel.setPriceText(String.Empty)
            val editText = binding.etItemPrice
            editText.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, 0)
        }
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

        binding.etItemPrice.setOnFocusChangeListener { _, hasFocus ->
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
