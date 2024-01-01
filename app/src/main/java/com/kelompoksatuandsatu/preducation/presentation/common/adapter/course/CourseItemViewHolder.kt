package com.kelompoksatuandsatu.preducation.presentation.common.adapter.course

import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemCourseCardBinding
import com.kelompoksatuandsatu.preducation.databinding.ItemLinearCourseBinding
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.toCurrencyFormat

class HomeCourseItemViewHolder(
    private val binding: ItemCourseCardBinding,
    private val assetWrapper: AssetWrapper,
    private val itemClick: (CourseViewParam) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CourseViewParam> {

    override fun bind(item: CourseViewParam) {
        with(item) {
            binding.ivPopularCourse.load(item.thumbnail) {
                crossfade(true)
            }
            binding.tvCategoryPopular.text = item.category?.name
            binding.tvRatingPopularCourse.text = item.totalRating.toString()
            binding.tvTitleCourse.text = item.title
            binding.tvLevelCourse.text = item.level
            binding.tvDurationCourse.text = item.totalDuration.toString() + assetWrapper.getString(R.string.text_mins)
            binding.tvModuleCourse.text = item.totalModule.toString() + assetWrapper.getString(R.string.text_module)
            if (item.price == 0) {
                binding.tvPriceCourse.text = assetWrapper.getString(R.string.text_free)
            } else {
                binding.tvPriceCourse.text = item.price?.toCurrencyFormat()
            }
            binding.tvPriceCourse.isGone = false
            binding.clProgressBar.isGone = true
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}

class HomeCourseLinearItemViewHolder(
    private val binding: ItemLinearCourseBinding,
    private val assetWrapper: AssetWrapper,
    private val itemClick: (CourseViewParam) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CourseViewParam> {

    override fun bind(item: CourseViewParam) {
        with(item) {
            binding.ivPopularCourse.load(item.thumbnail) {
                crossfade(true)
            }
            binding.tvCategoryPopular.text = item.category?.name
            binding.tvRatingPopularCourse.text = item.totalRating.toString()
            binding.tvTitleCourse.text = item.title
            binding.tvLevelCourse.text = item.level
            binding.tvDurationCourse.text = item.totalDuration.toString() + assetWrapper.getString(R.string.text_mins)
            binding.tvModuleCourse.text = item.totalModule.toString() + assetWrapper.getString(R.string.text_module)
            if (item.price == 0) {
                binding.tvPriceCourse.text = assetWrapper.getString(R.string.text_free)
            } else {
                binding.tvPriceCourse.text = item.price?.toCurrencyFormat()
            }
            binding.tvPriceCourse.isGone = false
            binding.clTypeClassFreemium.isGone = true
            binding.clTypeClassPremium.isGone = true
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}

class CourseLinearItemViewHolder(
    private val binding: ItemLinearCourseBinding,
    private val assetWrapper: AssetWrapper,
    private val itemClick: (CourseViewParam) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CourseViewParam> {

    override fun bind(item: CourseViewParam) {
        with(item) {
            binding.ivPopularCourse.load(item.thumbnail) {
                crossfade(true)
            }
            binding.tvCategoryPopular.text = item.category?.name
            binding.tvRatingPopularCourse.text = item.totalRating.toString()
            binding.tvTitleCourse.text = item.title
            binding.tvLevelCourse.text = item.level
            binding.tvDurationCourse.text = item.totalDuration.toString() + assetWrapper.getString(R.string.text_mins)
            binding.tvModuleCourse.text = item.totalModule.toString() + assetWrapper.getString(R.string.text_module)
            binding.tvPriceCourse.isGone = true
            binding.tvTypeClassPremium.text = item.typeClass
            binding.tvTypeClassFreemium.text = item.typeClass
            if (item.typeClass == assetWrapper.getString(R.string.text_premium_cl)) {
                binding.clTypeClassFreemium.isGone = true
                binding.clTypeClassPremium.isGone = false
            } else if (item.typeClass == assetWrapper.getString(R.string.text_free_cl)) {
                binding.clTypeClassFreemium.isGone = false
                binding.clTypeClassPremium.isGone = true
            }
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}
