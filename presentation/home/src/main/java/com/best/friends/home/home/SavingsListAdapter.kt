package com.best.friends.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.best.friends.core.AbstractViewHolder
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.home.databinding.LayoutSavingAddBinding
import com.best.friends.home.databinding.LayoutSavingItemBinding
import com.yapp.android2.domain.entity.Product

/**
 * 홈탭 화면에서 사용할 절약기록 리스트 adapter
 */
internal class SavingsListAdapter(
    private val onItemClick: (product: Product) -> Unit,
    private val onAddClick: () -> Unit
) : ListAdapter<Savings, AbstractViewHolder<Savings>>(CALLBACK) {

    enum class ViewType {
        ITEM, ADD
    }

    fun submit(products: List<Product>) {
        val list = products.map { it.toSavings() } + Savings.Add
        submitList(list)
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
                onItemClick = onItemClick
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
        private val onItemClick: (product: Product) -> Unit
    ) : AbstractViewHolder<Savings.Item>(binding.root) {

        override fun bind(data: Savings.Item) {
            val product = data.product
            binding.root.setOnSingleClickListener {
                onItemClick.invoke(product)
            }
            binding.product = product
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
            return oldItem == newItem
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

    data class Item(val product: Product) : Savings() {
        override val viewType: Int
            get() = SavingsListAdapter.ViewType.ITEM.ordinal
    }

    object Add : Savings() {
        override val viewType: Int
            get() = SavingsListAdapter.ViewType.ADD.ordinal
    }
}

fun Product.toSavings(): Savings.Item {
    return Savings.Item(this)
}
