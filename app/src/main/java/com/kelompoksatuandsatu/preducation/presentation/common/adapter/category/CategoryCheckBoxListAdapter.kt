package com.kelompoksatuandsatu.preducation.presentation.common.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemCheckboxFilterBinding
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass

class CategoryCheckBoxListAdapter(
    private val itemListener: CheckboxCategoryListener
) : RecyclerView.Adapter<CategoryCheckBoxListAdapter.CheckBoxFilterItemViewHolder>() {

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
        return CheckBoxFilterItemViewHolder(binding, itemListener)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: CheckBoxFilterItemViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    fun setData(data: List<CategoryClass>) {
        dataDiffer.submitList(data)
    }

    class CheckBoxFilterItemViewHolder(
        val binding: ItemCheckboxFilterBinding,
        private val itemListener: CheckboxCategoryListener
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CategoryClass> {

        override fun bind(item: CategoryClass) {
            binding.cbCategory.text = item.name
            binding.cbCategory.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    itemListener.onCategoryChecked(item)
                } else {
                    itemListener.onCategoryUnChecked(item)
                }
            }
        }
    }

    interface CheckboxCategoryListener {
        fun onCategoryChecked(category: CategoryClass)
        fun onCategoryUnChecked(category: CategoryClass)
    }
}
