package com.kelompoksatuandsatu.preducation.presentation.common.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemCategoryRoundedBinding
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.presentation.feature.home.HomeViewModel

class CategoryRoundedHomeListAdapter(
    private val viewModel: HomeViewModel,
    private val itemClick: (CategoryClass) -> Unit
) : RecyclerView.Adapter<CategoryRoundedHomeListAdapter.LinearCategoryPopularItemViewHolder>() {

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
    ): LinearCategoryPopularItemViewHolder {
        val binding = ItemCategoryRoundedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LinearCategoryPopularItemViewHolder(binding, viewModel, itemClick)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: LinearCategoryPopularItemViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
        holder.binding.root.setOnClickListener { v ->
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            if (position != 0) {
                viewModel.getCourse(dataDiffer.currentList[position].name)
            }
        }
        updateItemBackground(holder, position)
    }

    private fun updateItemBackground(holder: LinearCategoryPopularItemViewHolder, position: Int) {
        if (selectedPosition == position) {
            if (position == 0) {
                holder.binding.llCategoryPopular.setBackgroundResource(R.drawable.bg_category_clicked)
                holder.binding.tvCategoryPopular.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            } else {
                holder.binding.llCategoryPopular.setBackgroundResource(R.drawable.bg_category_clicked)
                holder.binding.tvCategoryPopular.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
            }
        } else {
            holder.binding.llCategoryPopular.setBackgroundResource(R.drawable.bg_outline_category)
            holder.binding.tvCategoryPopular.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.app_color_primary))
        }
    }

    fun setData(data: List<CategoryClass>) {
        dataDiffer.submitList(data)
    }

    class LinearCategoryPopularItemViewHolder(
        val binding: ItemCategoryRoundedBinding,
        val viewModel: HomeViewModel,
        val itemClick: (CategoryClass) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CategoryClass> {
        private var currentCategory: String? = null

        override fun bind(item: CategoryClass) {
            currentCategory = item.name
            binding.tvCategoryPopular.text = currentCategory
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}
