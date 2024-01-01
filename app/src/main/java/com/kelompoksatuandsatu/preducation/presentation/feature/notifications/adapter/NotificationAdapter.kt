package com.kelompoksatuandsatu.preducation.presentation.feature.notifications.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.databinding.ItemListNotificationBinding
import com.kelompoksatuandsatu.preducation.model.notification.NotificationItem
import java.util.regex.Pattern

class NotificationAdapter() : RecyclerView.Adapter<NotificationItemViewHolder>() {

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<NotificationItem>() {
            override fun areItemsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
                return oldItem.userId == newItem.userId
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

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    fun setData(data: List<NotificationItem>) {
        dataDiffer.submitList(data)
    }
}
class NotificationItemViewHolder(private val binding: ItemListNotificationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val datePattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})")

    fun bind(notificationItem: NotificationItem) {
        binding.tvNotificationName.text = notificationItem.title
        binding.tvNotificationDesc1.text = notificationItem.description
        binding.tvDate.text = notificationItem.createdAt?.let { extractDate(it) }
    }

    private fun extractDate(timestamp: String): String {
        val matcher = datePattern.matcher(timestamp)
        return if (matcher.find()) {
            matcher.group(1) ?: ""
        } else {
            ""
        }
    }
}
