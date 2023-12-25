package com.kelompoksatuandsatu.preducation.presentation.common.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemCheckboxFilterBinding
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.presentation.feature.filter.FilterViewModel

class CategoryCheckBoxListAdapter(
    private val viewModel: FilterViewModel,
    private val itemClick: (CategoryClass) -> Unit
) : RecyclerView.Adapter<CategoryCheckBoxListAdapter.CheckBoxFilterItemViewHolder>() {

    private var selectedPosition = 0
    private var lastSelectedPosition = 0

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CategoryClass>() {
            override fun areItemsTheSame(oldItem: CategoryClass, newItem: CategoryClass): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CategoryClass, newItem: CategoryClass): Boolean {
                return oldItem == newItem
            }
        }
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckBoxFilterItemViewHolder {
        val binding = ItemCheckboxFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CheckBoxFilterItemViewHolder(binding, viewModel, itemClick)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: CheckBoxFilterItemViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
        holder.binding.root.setOnClickListener { v ->
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            if (position == 0) {
                viewModel.getCourse()
            } else {
                viewModel.getCourse(dataDiffer.currentList[position].name)
            }
        }
        updateItemCheckBox(holder, position)
    }

    private fun updateItemCheckBox(holder: CheckBoxFilterItemViewHolder, position: Int) {
        holder.binding.cbCategory.isChecked = selectedPosition == position
    }

    fun setData(data: List<CategoryClass>) {
        dataDiffer.submitList(data)
    }

    class CheckBoxFilterItemViewHolder(
        val binding: ItemCheckboxFilterBinding,
        val viewModel: FilterViewModel,
        val itemClick: (CategoryClass) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CategoryClass> {
        private var currentCategory: String? = null

        override fun bind(item: CategoryClass) {
            currentCategory = item.name
            binding.cbCategory.text = currentCategory
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}
