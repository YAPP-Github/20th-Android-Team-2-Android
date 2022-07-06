package com.best.friends.home.update

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
import com.best.friend.design.R.string.common_cancel
import com.best.friend.design.R.string.common_delete
import com.best.friend.design.R.string.common_ok
import com.best.friend.design.R.string.common_update
import com.best.friends.core.BaseActivity
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.core.ui.Empty
import com.best.friends.core.ui.showToast
import com.best.friends.home.R
import com.best.friends.home.databinding.ActivitySavingItemUpdateBinding
import com.best.friends.home.dialog.HorizontalButtonsDialogFragment
import com.best.friends.home.update.SavingItemUpdateViewModel.Action.Delete
import com.best.friends.home.update.SavingItemUpdateViewModel.Action.Finish
import com.best.friends.home.update.SavingItemUpdateViewModel.Action.Update
import com.yapp.android2.domain.entity.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DecimalFormat

/**
 * 절약 수정 화면 Activity
 */
@AndroidEntryPoint
class SavingItemUpdateActivity :
    BaseActivity<ActivitySavingItemUpdateBinding>(R.layout.activity_saving_item_update) {

    private val viewModel by viewModels<SavingItemUpdateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setToolbar()
        observe()
        binding.viewModel = viewModel
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
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun initView() {
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

        binding.etItemPrice.inputType = TYPE_CLASS_NUMBER or TYPE_NUMBER_VARIATION_PASSWORD
        binding.etItemPrice.transformationMethod = null
        binding.etItemPrice.setOnFocusChangeListener { _, hasFocus ->
            val editText = binding.etItemPrice
            val price = viewModel.price.value
            if (price.isBlank()) {
                return@setOnFocusChangeListener
            }

            if (hasFocus) {
                editText.filters = arrayOf(InputFilter.LengthFilter(6))
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

        binding.tvUpdate.setOnSingleClickListener {
            showConfirmDialogFragment(
                title = getString(R.string.saving_item_update_popup_title),
                description = getString(R.string.saving_item_update_popup_description),
                negativeButtonName = getString(common_cancel),
                positiveButtonName = getString(common_update),
                positiveAction = { viewModel.onUpdateClick() }
            )
        }

        binding.tvDelete.setOnSingleClickListener {
            val isChecked = viewModel.paramsFlow.value.product.checked
            val title = if (isChecked) {
                getString(R.string.saving_item_cannot_delete_popup_title)
            } else {
                getString(R.string.saving_item_delete_popup_title)
            }

            val description = if (isChecked) {
                getString(R.string.saving_item_cannot_delete_popup_description)
            } else {
                getString(R.string.saving_item_delete_popup_description)
            }

            val positiveButtonName = if (isChecked) {
                getString(common_ok)
            } else {
                getString(common_delete)
            }

            showConfirmDialogFragment(
                title = title,
                description = description,
                negativeButtonEnable = !isChecked,
                negativeButtonName = getString(common_cancel),
                positiveButtonName = positiveButtonName,
                positiveAction = { if (!isChecked) viewModel.onDeleteClick() }
            )
        }
    }

    private fun setToolbar() {
        binding.ivBack.setOnSingleClickListener {
            onBackPressed()
        }

        binding.tvToolbarTitle.text = getString(R.string.saving_item_udpate_activity_toolbar_title)
    }

    private fun observe() {
        viewModel.error
            .onEach { errorMessage -> showToast(errorMessage) }
            .launchIn(lifecycleScope)

        viewModel.action
            .onEach { action ->
                when (action) {
                    Update -> {
                        setResult(RESULT_OK)
                        finish()
                    }
                    Delete -> {
                        setResult(RESULT_OK)
                        finish()
                    }
                    Finish -> finish()
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun showConfirmDialogFragment(
        title: String,
        description: String,
        negativeButtonEnable: Boolean = true,
        negativeButtonName: String,
        negativeAction: () -> Unit = {},
        positiveButtonName: String,
        positiveAction: () -> Unit = {}
    ) {
        HorizontalButtonsDialogFragment.show(
            supportFragmentManager,
            lifecycleOwner = this,
            title = title,
            description = description,
            negativeButtonEnable = negativeButtonEnable,
            negativeButtonName = negativeButtonName,
            negativeAction = negativeAction,
            positiveButtonName = positiveButtonName,
            positiveAction = positiveAction
        )
    }

    companion object {
        internal const val EXTRA_PRODUCT = "extra_product"

        fun intent(context: Context, product: Product): Intent {
            return Intent(context, SavingItemUpdateActivity::class.java)
                .putExtra(EXTRA_PRODUCT, product)
        }
    }
}
