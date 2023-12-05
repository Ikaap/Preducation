package com.kelompoksatuandsatu.preducation.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kelompoksatuandsatu.preducation.databinding.ItemCategoryCircleBinding
import com.kelompoksatuandsatu.preducation.model.CategoryClass

class CategoryCourseListAdapter(private val itemClick: (CategoryClass) -> Unit) :
    RecyclerView.Adapter<CategoryCourseItemViewHolder>() {
    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CategoryClass>() {
            override fun areItemsTheSame(
                oldItem: CategoryClass,
                newItem: CategoryClass
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CategoryClass,
                newItem: CategoryClass
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    )

    fun setData(data: List<CategoryClass>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryCourseItemViewHolder {
        return CategoryCourseItemViewHolder(
            binding = ItemCategoryCircleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick = itemClick
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: CategoryCourseItemViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }
}

class CategoryCourseItemViewHolder(
    private val binding: ItemCategoryCircleBinding,
    private val itemClick: (CategoryClass) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(item: CategoryClass) {
        with(item) {
            binding.ivCategoryCourse.load(item.imageCategory) {
                crossfade(true)
            }
            binding.tvCategoryCourse.text = item.name
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}
