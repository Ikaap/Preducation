package com.kelompoksatuandsatu.preducation.presentation.common.adapter.course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.core.ViewHolderBinder
import com.kelompoksatuandsatu.preducation.databinding.ItemLinearCourseBinding
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
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

    private var originalList: List<CourseViewParam> = emptyList()
    private val _isFilterEmpty = MutableLiveData<Boolean>()
    val isFilterEmpty: LiveData<Boolean> get() = _isFilterEmpty

    init {
        _isFilterEmpty.value = false
    }

    fun setData(data: List<CourseViewParam>) {
        originalList = data
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

    fun filter(query: CharSequence?) {
        val filteredList = if (query.isNullOrBlank()) {
            originalList
        } else {
            val lowercaseQuery = query.toString().replace("\\s+".toRegex(), "").toLowerCase(Locale.getDefault())
            originalList.filter { course ->
                course.title?.replace("\\s+".toRegex(), "")?.toLowerCase(Locale.getDefault())?.contains(lowercaseQuery) == true
            }
        }
        _isFilterEmpty.value = filteredList.isEmpty()

        dataDiffer.submitList(filteredList)
    }
}
