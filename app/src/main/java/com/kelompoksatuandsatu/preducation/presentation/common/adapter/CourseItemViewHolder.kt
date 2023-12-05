package com.kelompoksatuandsatu.preducation.presentation.common.adapter

import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemCourseCardBinding
import com.kelompoksatuandsatu.preducation.databinding.ItemLinearCourseBinding
import com.kelompoksatuandsatu.preducation.model.Course
import com.kelompoksatuandsatu.preducation.utils.toCurrencyFormat

class HomeCourseItemViewHolder(
    private val binding: ItemCourseCardBinding,
    private val itemClick: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {

    override fun bind(item: Course) {
        with(item) {
            binding.ivPopularCourse.load(item.thumbnail) {
                crossfade(true)
            }
            binding.tvCategoryPopular.text = item.category?.name
            binding.tvRatingPopularCourse.text = item.totalRating.toString()
            binding.tvTitleCourse.text = item.title
            binding.tvLevelCourse.text = item.level
            binding.tvDurationCourse.text = item.totalDuration.toString() + "Mins"
            binding.tvModuleCourse.text = item.totalModule.toString() + "Module"
            binding.tvPriceCourse.text = item.price?.toCurrencyFormat()
            binding.tvPriceCourse.isGone = false
            binding.clProgressBar.isGone = true
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}

class ClassCourseItemViewHolder(
    private val binding: ItemCourseCardBinding,
    private val itemClick: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {

    override fun bind(item: Course) {
        with(item) {
            binding.ivPopularCourse.load(item.thumbnail) {
                crossfade(true)
            }
            binding.tvCategoryPopular.text = item.category?.name
            binding.tvRatingPopularCourse.text = item.totalRating.toString()
            binding.tvTitleCourse.text = item.title
            binding.tvLevelCourse.text = item.level
            binding.tvDurationCourse.text = item.totalDuration.toString() + "Mins"
            binding.tvModuleCourse.text = item.totalModule.toString() + "Module"
            binding.tvPriceCourse.isGone = true
//            binding.tvProgress.text = item.progress.toString() + "% complete"
            binding.clProgressBar.isGone = false
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}

class HomeCourseLinearItemViewHolder(
    private val binding: ItemLinearCourseBinding,
    private val itemClick: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {

    override fun bind(item: Course) {
        with(item) {
            binding.ivPopularCourse.load(item.thumbnail) {
                crossfade(true)
            }
            binding.tvCategoryPopular.text = item.category?.name
            binding.tvRatingPopularCourse.text = item.totalRating.toString()
            binding.tvTitleCourse.text = item.title
            binding.tvLevelCourse.text = item.level
            binding.tvDurationCourse.text = item.totalDuration.toString() + "Mins"
            binding.tvModuleCourse.text = item.totalModule.toString() + "Module"
            binding.tvPriceCourse.text = item.price?.toCurrencyFormat()
            binding.tvPriceCourse.isGone = false
            binding.clTypeClassFreemium.isGone = true
            binding.clTypeClassPremium.isGone = true
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}

class CourseLinearItemViewHolder(
    private val binding: ItemLinearCourseBinding,
    private val itemClick: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {

    override fun bind(item: Course) {
        with(item) {
            binding.ivPopularCourse.load(item.thumbnail) {
                crossfade(true)
            }
            binding.tvCategoryPopular.text = item.category?.name
            binding.tvRatingPopularCourse.text = item.totalRating.toString()
            binding.tvTitleCourse.text = item.title
            binding.tvLevelCourse.text = item.level
            binding.tvDurationCourse.text = item.totalDuration.toString() + "Mins"
            binding.tvModuleCourse.text = item.totalModule.toString() + "Module"
            binding.tvPriceCourse.isGone = true
            binding.tvTypeClassPremium.text = item.typeClass
            binding.tvTypeClassFreemium.text = item.typeClass
            if (item.typeClass == "PREMIUM") {
                binding.clTypeClassFreemium.isGone = true
                binding.clTypeClassPremium.isGone = false
            } else if (item.typeClass == "FREE") {
                binding.clTypeClassFreemium.isGone = false
                binding.clTypeClassPremium.isGone = true
            }
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}

class HistoryPaymentItemViewHolder(
    private val binding: ItemLinearCourseBinding,
    private val itemClick: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {

    override fun bind(item: Course) {
        with(item) {
            binding.ivPopularCourse.load(item.thumbnail) {
                crossfade(true)
            }
            binding.tvCategoryPopular.text = item.category?.name
            binding.tvRatingPopularCourse.text = item.totalRating.toString()
            binding.tvTitleCourse.text = item.title
            binding.tvLevelCourse.text = item.level
            binding.tvDurationCourse.text = item.totalDuration.toString() + "Mins"
            binding.tvModuleCourse.text = item.totalModule.toString() + "Module"
            binding.tvPriceCourse.isGone = true
            binding.clTypeClassFreemium.isGone = true
            binding.clTypeClassPremium.isGone = true
//            binding.tvStatusPaymentPaid.text = item.statusPayment
//            binding.tvStatusPaymentWaiting.text = item.statusPayment
//            if (item.statusPayment == "Paid") {
//                binding.tvStatusPaymentPaid.isGone = false
//                binding.tvStatusPaymentWaiting.isGone = true
//            } else if (item.statusPayment == "Waiting") {
//                binding.tvStatusPaymentPaid.isGone = true
//                binding.tvStatusPaymentWaiting.isGone = false
//            }
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}
