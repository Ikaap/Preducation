package com.kelompoksatuandsatu.preducation.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemCategoryRoundedBinding
import com.kelompoksatuandsatu.preducation.model.CategoryPopular

class CategoryCourseRoundedListAdapter(private val itemClick: (CategoryPopular) -> Unit) :
    RecyclerView.Adapter<LinearCategoryPopularItemViewHolder>() {
    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CategoryPopular>() {
            override fun areItemsTheSame(oldItem: CategoryPopular, newItem: CategoryPopular): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CategoryPopular, newItem: CategoryPopular): Boolean {
                return oldItem.id == newItem.id
            }
        }
    )

    fun setData(data: List<CategoryPopular>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }

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
            itemClick = itemClick
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: LinearCategoryPopularItemViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }
}

class LinearCategoryPopularItemViewHolder(
    private val binding: ItemCategoryRoundedBinding,
    private val itemClick: (CategoryPopular) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CategoryPopular> {
    override fun bind(item: CategoryPopular) {
        with(item) {
            binding.tvCategoryPopular.text = item.nameCategoryPopular
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}
