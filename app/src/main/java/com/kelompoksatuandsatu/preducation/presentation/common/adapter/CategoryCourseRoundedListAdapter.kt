package com.kelompoksatuandsatu.preducation.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemCategoryRoundedBinding
import com.kelompoksatuandsatu.preducation.model.CategoryClass

class CategoryCourseRoundedListAdapter(
    private val itemClick: (CategoryClass) -> Unit
) : RecyclerView.Adapter<CategoryCourseRoundedListAdapter.LinearCategoryPopularItemViewHolder>() {

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CategoryClass>() {
            override fun areItemsTheSame(oldItem: CategoryClass, newItem: CategoryClass): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CategoryClass, newItem: CategoryClass): Boolean {
                return oldItem.id == newItem.id
            }
        }
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LinearCategoryPopularItemViewHolder {
        return LinearCategoryPopularItemViewHolder(
            binding = ItemCategoryRoundedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick

        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: LinearCategoryPopularItemViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    fun setData(data: List<CategoryClass>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }

    class LinearCategoryPopularItemViewHolder(
        private val binding: ItemCategoryRoundedBinding,
        val itemClick: (CategoryClass) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CategoryClass> {
        override fun bind(item: CategoryClass) {
            binding.tvCategoryPopular.text = item.name
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}
