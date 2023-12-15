package com.kelompoksatuandsatu.preducation.presentation.feature.notifications.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.databinding.ItemListNotificationBinding
import com.kelompoksatuandsatu.preducation.model.NotificationItem

class NotificationAdapter : ListAdapter<NotificationItem, NotificationAdapter.NotificationItemViewHolder>(
    NotificationDiffCallback()
) {

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<NotificationItem>() {
            override fun areItemsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {
        val binding = ItemListNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {
        val notificationItem = getItem(position)
        holder.bind(notificationItem)
    }

    class NotificationItemViewHolder(private val binding: ItemListNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notificationItem: NotificationItem) {
            binding.tvNotificationName.text = notificationItem.title
            binding.tvNotificationDesc1.text = notificationItem.description
        }
    }

    fun setData(data: List<NotificationItem>) {
        dataDiffer.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, dataDiffer.currentList.size)
    }

    private class NotificationDiffCallback : DiffUtil.ItemCallback<NotificationItem>() {
        override fun areItemsTheSame(
            oldItem: NotificationItem,
            newItem: NotificationItem
        ): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: NotificationItem,
            newItem: NotificationItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}
