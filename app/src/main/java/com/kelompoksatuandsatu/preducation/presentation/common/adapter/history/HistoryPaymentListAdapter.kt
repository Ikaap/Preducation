package com.kelompoksatuandsatu.preducation.presentation.common.adapter.classprogress

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.Payment
import com.kelompoksatuandsatu.preducation.databinding.ItemLinearCourseBinding
import java.util.Locale

// TODO model disesuaikan
class HistoryPaymentListAdapter(
    private val itemClick: (Payment) -> Unit
) :
    RecyclerView.Adapter<HistoryPaymentListAdapter.HistoryPaymentItemViewHolder>() {
    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<Payment>() {
            override fun areItemsTheSame(
                oldItem: Payment,
                newItem: Payment
            ): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(
                oldItem: Payment,
                newItem: Payment
            ): Boolean {
                return oldItem._id == newItem._id
            }
        }
    )

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Payment>) {
        dataDiffer.submitList(data)
        notifyDataSetChanged()
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
        (holder as ViewHolderBinder<Payment>).bind(dataDiffer.currentList[position])
    }

    fun filter(query: CharSequence?) {
        val filteredList = if (query.isNullOrBlank()) {
            dataDiffer.currentList
        } else {
            dataDiffer.currentList.filter { course ->
                course.courseId.title.lowercase(Locale.getDefault())
                    .contains(query.toString().lowercase(Locale.getDefault()))
            }
        }
        dataDiffer.submitList(filteredList)
    }

    // TODO model disesuaikan
    class HistoryPaymentItemViewHolder(
        private val binding: ItemLinearCourseBinding,
        private val itemClick: (Payment) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Payment> {

        @SuppressLint("SetTextI18n")
        override fun bind(item: Payment) {
            with(item) {
                binding.ivPopularCourse.load(item.courseId.thumbnail) {
                    crossfade(true)
                }
                binding.tvCategoryPopular.text = item.courseId.category.name
                binding.tvRatingPopularCourse.text = item.courseId.totalRating.toString()
                binding.tvTitleCourse.text = item.courseId.title
                binding.tvLevelCourse.text = item.courseId.level
                binding.tvDurationCourse.text = item.courseId.totalDuration.toString() + "Mins"
                binding.tvModuleCourse.text = item.courseId.totalModule.toString() + "Module"
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
