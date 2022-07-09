package com.best.friends.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.best.friends.core.AbstractViewHolder
import com.best.friends.core.extensions.isToday
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.home.databinding.LayoutSavingAddBinding
import com.best.friends.home.databinding.LayoutSavingItemBinding
import com.yapp.android2.domain.entity.Product
import java.time.ZonedDateTime

/**
 * 홈탭 화면에서 사용할 절약기록 리스트 adapter
 */
internal class SavingsListAdapter(
    private val onItemClick: (product: Product) -> Unit,
    private val onAddClick: () -> Unit,
    private val onItemChecked: (product: Product) -> Unit
) : ListAdapter<Savings, AbstractViewHolder<Savings>>(CALLBACK) {

    enum class ViewType {
        ITEM, ADD
    }

    fun submit(state: HomeViewModel.State, callback: () -> Unit = {}) {
        val list = arrayListOf<Savings>()
        list += state.toSavingItems()
        if (state.day.isToday) {
            list += Savings.Add
        }
        submitList(list, callback)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Savings> {
        val holder = when (ViewType.values()[viewType]) {
            ViewType.ITEM -> SavingsItemViewHolder(
                binding = LayoutSavingItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onItemClick = onItemClick,
                onItemChecked = onItemChecked
            )
            ViewType.ADD -> SavingAddViewHolder(
                binding = LayoutSavingAddBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onAddClick = onAddClick
            )
        }

        return holder as AbstractViewHolder<Savings>
    }

    override fun onBindViewHolder(holder: AbstractViewHolder<Savings>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).itemId
    }

    class SavingsItemViewHolder(
        private val binding: LayoutSavingItemBinding,
        private val onItemClick: (product: Product) -> Unit,
        private val onItemChecked: (product: Product) -> Unit
    ) : AbstractViewHolder<Savings.Item>(binding.root) {

        private lateinit var item: Savings.Item

        init {
            binding.checkboxClickSections.setOnSingleClickListener(100) {
                if (item.day.isToday) {
                    binding.checkbox.isChecked = !binding.checkbox.isChecked
                }
            }
            binding.priceClickSections.setOnSingleClickListener(100) {
                if (::item.isInitialized && item.day.isToday) {
                    onItemClick.invoke(item.product)
                }
            }
        }

        override fun bind(data: Savings.Item) {
            // for cache.
            item = data
            val (product, _) = item
            val isToday = item.day.isToday

            binding.product = product
            binding.checkbox.setOnCheckedChangeListener(null)
            binding.checkbox.isChecked = (product.checked)
            binding.checkbox.isEnabled = isToday
            binding.checkbox.setOnCheckedChangeListener { _, _ ->
                if (isToday) {
                    onItemChecked.invoke(product)
                }
            }
            binding.executePendingBindings()
        }
    }

    class SavingAddViewHolder(
        private val binding: LayoutSavingAddBinding,
        onAddClick: () -> Unit
    ) : AbstractViewHolder<Savings.Add>(binding.root) {

        init {
            binding.root.setOnSingleClickListener {
                onAddClick.invoke()
            }
        }

        override fun bind(data: Savings.Add) {
            binding.executePendingBindings()
        }
    }

    companion object CALLBACK : DiffUtil.ItemCallback<Savings>() {
        override fun areItemsTheSame(oldItem: Savings, newItem: Savings): Boolean {
            return if (oldItem is Savings.Item && newItem is Savings.Item) {
                oldItem.product.productId == newItem.product.productId
            } else {
                oldItem.itemId == newItem.itemId
            }
        }

        override fun areContentsTheSame(oldItem: Savings, newItem: Savings): Boolean {
            return oldItem == newItem
        }
    }
}

sealed class Savings {
    abstract val viewType: Int
    val itemId: Long
        get() = viewType.toLong()

    data class Item(val product: Product, val day: ZonedDateTime) : Savings() {
        override val viewType: Int
            get() = SavingsListAdapter.ViewType.ITEM.ordinal
    }

    object Add : Savings() {
        override val viewType: Int
            get() = SavingsListAdapter.ViewType.ADD.ordinal
    }
}

private fun HomeViewModel.State.toSavingItems(): List<Savings.Item> {
    return products.map { Savings.Item(it, day) }
}
