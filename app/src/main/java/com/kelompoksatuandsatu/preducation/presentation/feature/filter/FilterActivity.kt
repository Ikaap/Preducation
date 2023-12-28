package com.kelompoksatuandsatu.preducation.presentation.feature.filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kelompoksatuandsatu.preducation.databinding.ActivityFilterBinding
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryCheckBoxListAdapter
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding

    private val viewModel: FilterViewModel by viewModel()

    private var selectedCategory: String? = null

    private val categoryCourseAdapter: CategoryCheckBoxListAdapter by lazy {
        CategoryCheckBoxListAdapter(viewModel) {
            viewModel.getCourse(it.name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListener()
        getData()
        observeData()
    }

    private fun getData() {
        viewModel.getCategoriesClass()
    }

    private fun observeData() {
        viewModel.categoriesClass.observe(this) {
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

    private fun setOnClickListener() {
        binding.ivArrowLeft.setOnClickListener {
            onBackPressed()
        }

        binding.tvClear.setOnClickListener {
            onClearCheckBox()
        }

        binding.clButtonEnrollClass.setOnClickListener {
            onFilledCheckBox()
        }
    }

    private fun onFilledCheckBox() {
        selectedCategory = "SelectedCategory"
        updateCourseFragmentView(selectedCategory)
    }

    private fun onClearCheckBox() {
        selectedCategory = null
        updateCourseFragmentView(selectedCategory)
    }

    private fun updateCourseFragmentView(selectedCategory: String?) {
        val resultIntent = Intent()
        resultIntent.putExtra("selectedCategory", selectedCategory)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun applyFilter() {
        val searchQuery = viewModel.searchQuery.value
        val selectedType = viewModel.selectedType.value
        val selectedCategories = viewModel.selectedCategories.value

//        filterListener?.onFilterApplied(selectedCategories, searchQuery, selectedType)
//        dismiss()
    }

}
