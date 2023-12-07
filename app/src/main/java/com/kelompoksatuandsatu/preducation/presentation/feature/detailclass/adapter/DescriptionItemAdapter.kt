package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.databinding.ItemDescRecommededStudentsBinding
import com.kelompoksatuandsatu.preducation.model.TargetAudience

class DescriptionItemAdapter :
    RecyclerView.Adapter<DescriptionItemAdapter.DescriptionItemListViewHolder>() {

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<TargetAudience>() {
            override fun areItemsTheSame(
                oldItem: TargetAudience,
                newItem: TargetAudience
            ): Boolean {
                return oldItem.desc == newItem.desc
            }

            override fun areContentsTheSame(
                oldItem: TargetAudience,
                newItem: TargetAudience
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DescriptionItemListViewHolder {
        val binding = ItemDescRecommededStudentsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DescriptionItemListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DescriptionItemListViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    fun setData(data: List<TargetAudience>) {
        dataDiffer.submitList(data)
    }

    class DescriptionItemListViewHolder(
        private val binding: ItemDescRecommededStudentsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TargetAudience) {
            with(binding) {
                tvDescRecommendedStudents.text = item.desc.toString()
            }
        }
    }
}
