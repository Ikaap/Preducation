package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kelompoksatuandsatu.preducation.R

class TransactionAdapter(private val courses: List<PopularCourseItem>) :
    RecyclerView.Adapter<TransactionAdapter.PopularCourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCourseViewHolder {
        val layoutResId = when (viewType) {
            VIEW_TYPE_PAID -> R.layout.item_transactions_paid
            VIEW_TYPE_WAITING -> R.layout.item_transactions_waiting
            else -> throw IllegalArgumentException("Invalid view type")
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(layoutResId, parent, false)

        return PopularCourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularCourseViewHolder, position: Int) {
        val course = courses[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            // Use your logic to determine the type of transaction
            // For example, you can check a property in the PopularCourseItem
            courses[position].status == "Paid" -> VIEW_TYPE_PAID
            else -> VIEW_TYPE_WAITING
        }
    }

    inner class PopularCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val popularCourseImageView: ImageView = itemView.findViewById(R.id.iv_popular_course)
        private val categoryTextView: TextView = itemView.findViewById(R.id.tv_category_popular)
        private val ratingImageView: ImageView = itemView.findViewById(R.id.iv_rating)
        private val ratingTextView: TextView = itemView.findViewById(R.id.tv_rating_popular_course)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title_course)
        private val moduleCountTextView: TextView = itemView.findViewById(R.id.tv_module_course)
        private val durationTextView: TextView = itemView.findViewById(R.id.tv_duration_course)
        private val levelTextView: TextView = itemView.findViewById(R.id.tv_level_course)
        private val priceStatusTextView: TextView = itemView.findViewById(R.id.tv_price_course)

        fun bind(course: PopularCourseItem) {
            Glide.with(itemView.context)
                .load(course.imageUrl)
                .into(popularCourseImageView)

            categoryTextView.text = course.category
            ratingTextView.text = course.rating.toString()
            titleTextView.text = course.title
            moduleCountTextView.text = "${course.moduleCount} Modules"
            durationTextView.text = course.duration
            levelTextView.text = course.level
            priceStatusTextView.text = course.status
        }
    }

    companion object {
        const val VIEW_TYPE_PAID = 1
        const val VIEW_TYPE_WAITING = 2
    }
}
