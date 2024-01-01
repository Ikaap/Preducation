package com.kelompoksatuandsatu.preducation.presentation.common.adapter.classprogress

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isGone
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemCourseCardBinding
import com.kelompoksatuandsatu.preducation.model.progress.CourseProgressItemClass
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper

class CourseProgressListAdapter(
    private val assetWrapper: AssetWrapper,
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
            assetWrapper,
            itemClick
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ClassCourseItemViewHolder, position: Int) {
        (holder as ViewHolderBinder<CourseProgressItemClass>).bind(dataDiffer.currentList[position])
    }

    class ClassCourseItemViewHolder(
        private val binding: ItemCourseCardBinding,
        private val assetWrapper: AssetWrapper,
        private val itemClick: (CourseProgressItemClass) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CourseProgressItemClass> {

        @SuppressLint("SetTextI18n")
        override fun bind(item: CourseProgressItemClass) {
            with(item) {
                binding.ivPopularCourse.load(item.courseId?.thumbnail) {
                    crossfade(true)
                }
                binding.tvCategoryPopular.text = item.courseId?.category?.name
                binding.tvRatingPopularCourse.text = item.courseId?.totalRating.toString()
                binding.tvTitleCourse.text = item.courseId?.title
                binding.tvLevelCourse.text = item.courseId?.level + assetWrapper.getString(R.string.text_level)
                binding.tvDurationCourse.text = item.courseId?.totalDuration.toString() + assetWrapper.getString(R.string.text_mins)
                binding.tvModuleCourse.text = item.courseId?.totalModule.toString() + assetWrapper.getString(R.string.text_module)
                binding.tvPriceCourse.isGone = true
                binding.tvProgress.text = item.percentage.toString() + assetWrapper.getString(R.string.text_complete)
                binding.progressBar.progress = item.percentage!!
                if (item.percentage == 100) {
                    val backgroundDrawable = getDrawable(itemView.context, R.drawable.bg_progress_bar_complete)
                    binding.progressBar.setProgressDrawableTiled(backgroundDrawable)
                }
                binding.clProgressBar.isGone = false
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
