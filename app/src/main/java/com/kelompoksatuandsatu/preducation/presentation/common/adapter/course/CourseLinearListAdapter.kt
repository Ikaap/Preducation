package com.kelompoksatuandsatu.preducation.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemLinearCourseBinding
import com.kelompoksatuandsatu.preducation.model.CourseViewParam
import java.util.Locale

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
            AdapterLayoutMenu.COURSE.ordinal -> {
                CourseLinearItemViewHolder(
                    binding = ItemLinearCourseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    itemClick
                )
            }
            else -> {
                HistoryPaymentItemViewHolder(
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

    fun filter(query: CharSequence?) {
        val filteredList = if (query.isNullOrBlank()) {
            dataDiffer.currentList
        } else {
            dataDiffer.currentList.filter { course ->
                course.title?.toLowerCase(Locale.getDefault())
                    ?.contains(query.toString().toLowerCase(Locale.getDefault())) == true
            }
        }
        dataDiffer.submitList(filteredList)
    }
}