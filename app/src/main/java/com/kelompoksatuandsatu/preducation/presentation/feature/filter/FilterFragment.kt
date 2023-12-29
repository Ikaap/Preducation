package com.kelompoksatuandsatu.preducation.presentation.feature.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.FragmentFilterBinding
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryCheckBoxListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CheckboxCategoryListener
import com.kelompoksatuandsatu.preducation.presentation.feature.course.CourseViewModel
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBinding

    private val viewModel: CourseViewModel by activityViewModel()

    private var selectedCategory: String? = null

    private var filterListener: OnFilterListener? = null

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
        val selectedType = viewModel.selectedType.value
        val selectedCategories = viewModel.selectedCategories.value

        filterListener?.onFilterApplied(selectedType, selectedCategories)
    }

    private val categoryCourseAdapter: CategoryCheckBoxListAdapter by lazy {
        CategoryCheckBoxListAdapter(object : CheckboxCategoryListener {
            override fun onCategoryChecked(category: CategoryClass) {
                viewModel.addSelectedCategory(category)
            }
            override fun onCategoryUnchecked(category: CategoryClass) {
                viewModel.deleteSelectedCategory(category)
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
        viewModel.getCategoriesClass()
    }

    private fun observeData() {
        viewModel.categoriesClass.observe(viewLifecycleOwner) {
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
        binding.ivArrowLeft.setOnClickListener {
            dismiss()
        }

        binding.tvClear.setOnClickListener {
        }

        binding.clButtonEnrollClass.setOnClickListener {
            applyFilter()
            dismiss()
        }
    }
}
