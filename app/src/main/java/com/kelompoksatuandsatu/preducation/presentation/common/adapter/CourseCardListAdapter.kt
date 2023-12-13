package com.kelompoksatuandsatu.preducation.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemCourseCardBinding
import com.kelompoksatuandsatu.preducation.model.Course
import java.util.Locale

class CourseCardListAdapter(
    var adapterLayoutMenu: AdapterLayoutMenu,
    private val itemClick: (Course) -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {
    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Course,
                newItem: Course
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    )

    fun setData(data: List<Course>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }

    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMenu.ordinal
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return when (viewType) {
            AdapterLayoutMenu.HOME.ordinal -> {
                HomeCourseItemViewHolder(
                    binding = ItemCourseCardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    itemClick
                )
            }
            else -> {
                ClassCourseItemViewHolder(
                    binding = ItemCourseCardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    itemClick
                )
            }
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Course>).bind(dataDiffer.currentList[position])
    }

    fun filter(query: CharSequence?) {
        val filteredList = if (query.isNullOrBlank()) {
            dataDiffer.currentList
        } else {
            dataDiffer.currentList.filter { course ->
                course.titleCourse?.toLowerCase(Locale.getDefault())
                    ?.contains(query.toString().toLowerCase(Locale.getDefault())) == true
            }
        }
        dataDiffer.submitList(filteredList)
    }
}
