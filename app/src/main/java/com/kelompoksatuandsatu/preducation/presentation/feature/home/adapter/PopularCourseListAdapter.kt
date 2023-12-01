package com.kelompoksatuandsatu.preducation.presentation.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kelompoksatuandsatu.preducation.databinding.ItemPopularCourseBinding
import com.kelompoksatuandsatu.preducation.model.PopularCourse
import com.kelompoksatuandsatu.preducation.utils.toCurrencyFormat

class PopularCourseListAdapter(private val itemClick: (PopularCourse) -> Unit) :
    RecyclerView.Adapter<LinearPopularCourseItemViewHolder>() {
    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<PopularCourse>() {
            override fun areItemsTheSame(oldItem: PopularCourse, newItem: PopularCourse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PopularCourse,
                newItem: PopularCourse
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    )

    fun setData(data: List<PopularCourse>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LinearPopularCourseItemViewHolder {
        return LinearPopularCourseItemViewHolder(
            binding = ItemPopularCourseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick = itemClick
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: LinearPopularCourseItemViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }
}

class LinearPopularCourseItemViewHolder(
    private val binding: ItemPopularCourseBinding,
    private val itemClick: (PopularCourse) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(item: PopularCourse) {
        with(item) {
            binding.ivPopularCourse.load(item.imgUrlPopularCourse) {
                crossfade(true)
            }
            binding.tvCategoryPopular.text = item.nameCategoryPopular
            binding.tvRatingPopularCourse.text = item.ratingCourse
            binding.tvTitleCourse.text = item.titleCourse
            binding.tvLevelCourse.text = item.levelCourse
            binding.tvDurationCourse.text = item.durationCourse
            binding.tvModuleCourse.text = item.moduleCourse
            binding.tvPriceCourse.text = item.priceCourse.toCurrencyFormat()
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}
