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
import com.kelompoksatuandsatu.preducation.model.category.categoryprogress.CategoryType
import com.kelompoksatuandsatu.preducation.presentation.feature.course.CourseViewModel

class CategoryRoundedCourseListAdapter(
    private val viewModel: CourseViewModel,
    private val itemClick: (CategoryType) -> Unit
) : RecyclerView.Adapter<CategoryRoundedCourseListAdapter.LinearCategoryProgressItemViewHolder>() {

    private var selectedPosition = 0
    private var lastSelectedPosition = 0

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CategoryType>() {
            override fun areItemsTheSame(oldItem: CategoryType, newItem: CategoryType): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CategoryType, newItem: CategoryType): Boolean {
                return oldItem == newItem
            }
        }
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LinearCategoryProgressItemViewHolder {
        val binding = ItemCategoryRoundedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LinearCategoryProgressItemViewHolder(binding, viewModel, itemClick)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: LinearCategoryProgressItemViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
        holder.binding.root.setOnClickListener { v ->
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            if (position != 0) {
                viewModel.getCourseTopic(dataDiffer.currentList[position].nameCategory)
            }
        }
        updateItemBackground(holder, position)
    }

    private fun updateItemBackground(holder: LinearCategoryProgressItemViewHolder, position: Int) {
        if (selectedPosition == position) {
            if (position == 0) {
                holder.binding.llCategoryPopular.setBackgroundResource(R.drawable.bg_category_clicked)
                holder.binding.tvCategoryPopular.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        android.R.color.white
                    )
                )
            } else {
                holder.binding.llCategoryPopular.setBackgroundResource(R.drawable.bg_category_clicked)
                holder.binding.tvCategoryPopular.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        android.R.color.white
                    )
                )
            }
        } else {
            holder.binding.llCategoryPopular.setBackgroundResource(R.drawable.bg_outline_category)
            holder.binding.tvCategoryPopular.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.app_color_primary
                )
            )
        }
    }

    fun setData(data: List<CategoryType>) {
        dataDiffer.submitList(data)
    }

    class LinearCategoryProgressItemViewHolder(
        val binding: ItemCategoryRoundedBinding,
        val viewModel: CourseViewModel,
        val itemClick: (CategoryType) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CategoryType> {
        private var currentCategory: String? = null

        override fun bind(item: CategoryType) {
            currentCategory = item.nameCategory
            binding.tvCategoryPopular.text = currentCategory
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}
