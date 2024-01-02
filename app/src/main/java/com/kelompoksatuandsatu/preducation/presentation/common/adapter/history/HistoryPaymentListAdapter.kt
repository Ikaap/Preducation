package com.kelompoksatuandsatu.preducation.presentation.common.adapter.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.CourseId
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.CourseItem
import com.kelompoksatuandsatu.preducation.databinding.ItemLinearHistoryBinding
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper

class HistoryPaymentListAdapter(
    private val itemClick: (CourseItem) -> Unit,
    private val assetWrapper: AssetWrapper
) : RecyclerView.Adapter<HistoryPaymentListAdapter.HistoryPaymentItemViewHolder>() {

    private val differ = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CourseItem>() {
            override fun areItemsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPaymentItemViewHolder {
        val binding = ItemLinearHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryPaymentItemViewHolder(binding, itemClick, assetWrapper)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: HistoryPaymentItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun setData(data: List<CourseItem>) {
        differ.submitList(data)
    }

    class HistoryPaymentItemViewHolder(
        private val binding: ItemLinearHistoryBinding,
        private val itemClick: (CourseItem) -> Unit,
        private val assetWrapper: AssetWrapper
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CourseItem> {

        @SuppressLint("SetTextI18n")
        override fun bind(item: CourseItem) {
            with(binding) {
                val courseId: CourseId? = item.courseId
                ivPopularCourse.load(courseId?.thumbnail) {
                    crossfade(true)
                }

                tvCategoryPopular.text = courseId?.category?.name

                val isPaid = item.status == assetWrapper.getString(R.string.text_paid)
                clTypePaid.isVisible = isPaid
                clTypeWaiting.isVisible = !isPaid

                tvStatusPaymentPaid.isGone = !isPaid
                tvStatusPaymentPaid.text = item.status

                tvStatusPaymentWaiting.isGone = isPaid
                tvStatusPaymentWaiting.text = item.status

                tvTitleCourse.text = courseId?.title
                tvLevelCourse.text = courseId?.level + assetWrapper.getString(R.string.text_level)
                tvDurationCourse.text = courseId?.totalDuration.toString() + assetWrapper.getString(R.string.text_mins)
                tvModuleCourse.text = courseId?.totalModule.toString() + assetWrapper.getString(R.string.text_module)

                itemView.setOnClickListener { itemClick(item) }
            }
        }
    }
}
