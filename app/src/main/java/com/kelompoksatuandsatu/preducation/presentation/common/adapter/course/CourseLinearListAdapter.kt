package com.kelompoksatuandsatu.preducation.presentation.common.adapter.course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemLinearCourseBinding
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam

class CourseLinearListAdapter(
    var adapterLayoutMenu: AdapterLayoutMenu,
    private val itemClick: (CourseViewParam) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<CourseViewParam>() {
            override fun areItemsTheSame(oldItem: CourseViewParam, newItem: CourseViewParam): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CourseViewParam,
                newItem: CourseViewParam
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    )

    fun setData(data: List<CourseViewParam>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }

    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMenu.ordinal
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterLayoutMenu.SEEALL.ordinal -> {
                HomeCourseLinearItemViewHolder(
                    binding = ItemLinearCourseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    itemClick
                )
            }
            else -> {
                CourseLinearItemViewHolder(
                    binding = ItemLinearCourseBinding.inflate(
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<CourseViewParam>).bind(dataDiffer.currentList[position])
    }
}
