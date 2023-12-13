package com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.databinding.ItemDescRecommededStudentsBinding

class DescriptionItemAdapter(
    private var targetAudienceList: List<String>
) : RecyclerView.Adapter<DescriptionItemAdapter.DescriptionItemListViewHolder>() {

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
        val item = targetAudienceList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = targetAudienceList.size

    fun setData(data: List<String>) {
        targetAudienceList = data
    }

    class DescriptionItemListViewHolder(
        private val binding: ItemDescRecommededStudentsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            with(binding) {
                tvDescRecommendedStudents.text = item
            }
        }
    }
}
