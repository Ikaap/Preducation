package com.kelompoksatuandsatu.preducation.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemCategoryRoundedBinding
import com.kelompoksatuandsatu.preducation.model.CategoryPopular
import com.kelompoksatuandsatu.preducation.presentation.feature.course.CourseViewModel
import com.kelompoksatuandsatu.preducation.presentation.feature.home.HomeViewModel

class CategoryTypeRoundedListAdapter(
    private val viewModel: CourseViewModel,
    private val itemClick: (CategoryPopular) -> Unit
) : RecyclerView.Adapter<CategoryTypeRoundedListAdapter.LinearCategoryPopularItemViewHolder>() {

    var selectedPosition = -1
    var lastSelectedPosition = -1

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CategoryPopular>() {
            override fun areItemsTheSame(oldItem: CategoryPopular, newItem: CategoryPopular): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CategoryPopular, newItem: CategoryPopular): Boolean {
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
            viewModel.getCourse(dataDiffer.currentList[position].nameCategoryPopular)
        }
        updateItemBackground(holder, position)
    }

    private fun updateItemBackground(holder: LinearCategoryPopularItemViewHolder, position: Int) {
        if (selectedPosition == position) {
            holder.binding.llCategoryPopular.setBackgroundResource(R.drawable.bg_category_clicked)
            holder.binding.tvCategoryPopular.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
        } else {
            holder.binding.llCategoryPopular.setBackgroundResource(R.drawable.bg_outline_category)
            holder.binding.tvCategoryPopular.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.app_color_primary))
        }
    }

    fun setData(data: List<CategoryPopular>) {
        dataDiffer.submitList(data)
    }

    class LinearCategoryPopularItemViewHolder(
        val binding: ItemCategoryRoundedBinding,
        val viewModel: CourseViewModel,
        val itemClick: (CategoryPopular) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CategoryPopular> {
        private var currentCategory: String? = null

        override fun bind(item: CategoryPopular) {
            currentCategory = item.nameCategoryPopular
            binding.tvCategoryPopular.text = currentCategory
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}
