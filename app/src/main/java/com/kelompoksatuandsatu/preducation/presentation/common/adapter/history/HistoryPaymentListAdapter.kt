package com.kelompoksatuandsatu.preducation.presentation.common.adapter.classprogress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemLinearCourseBinding
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import java.util.Locale

// TODO model disesuaikan
class HistoryPaymentListAdapter(
    private val itemClick: (CourseViewParam) -> Unit
) :
    RecyclerView.Adapter<HistoryPaymentListAdapter.HistoryPaymentItemViewHolder>() {
    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CourseViewParam>() {
            override fun areItemsTheSame(
                oldItem: CourseViewParam,
                newItem: CourseViewParam
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CourseViewParam,
                newItem: CourseViewParam
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    )

    fun setData(data: List<CourseViewParam>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryPaymentItemViewHolder {
        val binding = ItemLinearCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryPaymentItemViewHolder(
            binding,
            itemClick
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: HistoryPaymentItemViewHolder, position: Int) {
        (holder as ViewHolderBinder<CourseViewParam>).bind(dataDiffer.currentList[position])
    }

    fun filter(query: CharSequence?) {
        val filteredList = if (query.isNullOrBlank()) {
            dataDiffer.currentList
        } else {
            dataDiffer.currentList.filter { course ->
                course.title?.toLowerCase(Locale.getDefault())
                    ?.contains(query.toString().toLowerCase(Locale.getDefault())) == true
            }
        }
        dataDiffer.submitList(filteredList)
    }

    // TODO model disesuaikan
    class HistoryPaymentItemViewHolder(
        private val binding: ItemLinearCourseBinding,
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
}
