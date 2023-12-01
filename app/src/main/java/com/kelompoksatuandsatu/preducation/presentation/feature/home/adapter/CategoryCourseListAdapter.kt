package com.kelompoksatuandsatu.preducation.presentation.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kelompoksatuandsatu.preducation.databinding.ItemCategoryCourseBinding
import com.kelompoksatuandsatu.preducation.model.CategoryCourse

class CategoryCourseListAdapter(private val itemClick: (CategoryCourse) -> Unit) :
    RecyclerView.Adapter<LinearCategoryCourseItemViewHolder>() {
    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CategoryCourse>() {
            override fun areItemsTheSame(
                oldItem: CategoryCourse,
                newItem: CategoryCourse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CategoryCourse,
                newItem: CategoryCourse
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    )

    fun setData(data: List<CategoryCourse>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LinearCategoryCourseItemViewHolder {
        return LinearCategoryCourseItemViewHolder(
            binding = ItemCategoryCourseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick = itemClick
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: LinearCategoryCourseItemViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }
}

class LinearCategoryCourseItemViewHolder(
    private val binding: ItemCategoryCourseBinding,
    private val itemClick: (CategoryCourse) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(item: CategoryCourse) {
        with(item) {
            binding.ivCategoryCourse.load(item.imgUrlCategoryCourse) {
                crossfade(true)
            }
            binding.tvCategoryCourse.text = item.nameCategoryCourse
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}
