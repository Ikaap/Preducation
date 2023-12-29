package com.kelompoksatuandsatu.preducation.presentation.feature.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kelompoksatuandsatu.preducation.databinding.FragmentFilterBinding
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryCheckBoxListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.course.CourseViewModel
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBinding

    private val viewModel: CourseViewModel by activityViewModel()

    private var filterListener: OnFilterListener? = null

    private val tempSelectedCategories = mutableListOf<CategoryClass>()

    interface OnFilterListener {
        fun onFilterApplied(
            type: String?,
            category: List<CategoryClass>?
        )
    }

    fun setFilterListener(listener: OnFilterListener) {
        filterListener = listener
    }

    private fun applyFilter() {
        viewModel.setSelectedCategories(tempSelectedCategories.toList())

        filterListener?.onFilterApplied(viewModel.selectedType.value, viewModel.selectedCategories.value)

        tempSelectedCategories.clear()
    }

    private val categoryCourseAdapter: CategoryCheckBoxListAdapter by lazy {
        CategoryCheckBoxListAdapter(object : CategoryCheckBoxListAdapter.CheckboxCategoryListener {
            override fun onCategoryChecked(category: CategoryClass) {
                tempSelectedCategories.add(category)
            }

            override fun onCategoryUnChecked(category: CategoryClass) {
                tempSelectedCategories.remove(category)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
        observeData()
        setOnClickListener()
    }

    private fun fetchData() {
        viewModel.getCategories()
    }

    private fun observeData() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.shimmerCategoryCheckbox.isVisible = false
                    binding.layoutStateCategoryCheckbox.root.isVisible = false
                    binding.layoutStateCategoryCheckbox.tvError.isVisible = false
                    binding.rvCheckboxCategoryFilter.apply {
                        isVisible = true
                        adapter = categoryCourseAdapter
                    }
                    it.payload?.let { data ->
                        categoryCourseAdapter.setData(data)
                    }
                },
                doOnLoading = {
                    binding.rvCheckboxCategoryFilter.isVisible = false
                    binding.shimmerCategoryCheckbox.isVisible = true
                    binding.layoutStateCategoryCheckbox.root.isVisible = false
                    binding.layoutStateCategoryCheckbox.tvError.isVisible = false
                },
                doOnEmpty = {
                    binding.rvCheckboxCategoryFilter.isVisible = false
                    binding.shimmerCategoryCheckbox.isVisible = false
                    binding.layoutStateCategoryCheckbox.root.isVisible = true
                    binding.layoutStateCategoryCheckbox.tvError.isVisible = false
                },
                doOnError = {
                    binding.rvCheckboxCategoryFilter.isVisible = false
                    binding.shimmerCategoryCheckbox.isVisible = false
                    binding.layoutStateCategoryCheckbox.root.isVisible = false
                    binding.layoutStateCategoryCheckbox.tvError.isVisible = true
                    binding.layoutStateCategoryCheckbox.tvError.text = it.exception?.message
                }
            )
        }
    }

    private fun navigateToCourse() {
        findNavController().navigate(R.id.navigate_to_course)
    }

    private fun setOnClickListener() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }

        binding.clButtonEnrollClass.setOnClickListener {
            applyFilter()
            dismiss()
        }
    }
}
