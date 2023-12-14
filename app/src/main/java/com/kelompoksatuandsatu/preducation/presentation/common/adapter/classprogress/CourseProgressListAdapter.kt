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
import com.kelompoksatuandsatu.preducation.databinding.ItemCourseCardBinding
import com.kelompoksatuandsatu.preducation.model.progress.CourseProgressItemClass
import java.util.Locale

class CourseProgressListAdapter(
    private val itemClick: (CourseProgressItemClass) -> Unit
) :
    RecyclerView.Adapter<CourseProgressListAdapter.ClassCourseItemViewHolder>() {
    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CourseProgressItemClass>() {
            override fun areItemsTheSame(oldItem: CourseProgressItemClass, newItem: CourseProgressItemClass): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CourseProgressItemClass,
                newItem: CourseProgressItemClass
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    )

    fun setData(data: List<CourseProgressItemClass>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassCourseItemViewHolder {
        val binding = ItemCourseCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClassCourseItemViewHolder(
            binding,
            itemClick
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ClassCourseItemViewHolder, position: Int) {
        (holder as ViewHolderBinder<CourseProgressItemClass>).bind(dataDiffer.currentList[position])
    }

    fun filter(query: CharSequence?) {
        val filteredList = if (query.isNullOrBlank()) {
            dataDiffer.currentList
        } else {
            dataDiffer.currentList.filter { course ->
                course.courseId?.title?.toLowerCase(Locale.getDefault())
                    ?.contains(query.toString().toLowerCase(Locale.getDefault())) == true
            }
        }
        dataDiffer.submitList(filteredList)
    }

    class ClassCourseItemViewHolder(
        private val binding: ItemCourseCardBinding,
        private val itemClick: (CourseProgressItemClass) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CourseProgressItemClass> {

        override fun bind(item: CourseProgressItemClass) {
            with(item) {
                binding.ivPopularCourse.load(item.courseId?.thumbnail) {
                    crossfade(true)
                }
                binding.tvCategoryPopular.text = item.courseId?.category?.name
                binding.tvRatingPopularCourse.text = item.courseId?.totalRating.toString()
                binding.tvTitleCourse.text = item.courseId?.title
                binding.tvLevelCourse.text = item.courseId?.level + " Level"
                binding.tvDurationCourse.text = item.courseId?.totalDuration.toString() + " Mins"
                binding.tvModuleCourse.text = item.courseId?.totalModule.toString() + " Module"
                binding.tvPriceCourse.isGone = true
                binding.tvProgress.text = item.percentage.toString() + " % complete"
                binding.progressBar.progress = item.percentage!!
                binding.clProgressBar.isGone = false
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
