package com.kelompoksatuandsatu.preducation.presentation.feature.course

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.databinding.FragmentCourseBinding
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryRoundedCourseListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.CourseLinearListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.filter.FilterActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.search.SearchActivity
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding

    private val viewModel: CourseViewModel by viewModel()

    private val typeCourseAdapter: CourseLinearListAdapter by lazy {
        CourseLinearListAdapter(AdapterLayoutMenu.COURSE) {
            viewModel.isUserLogin.observe(viewLifecycleOwner) { isLogin ->
                if (!isLogin) {
                    showDialog()
                } else {
                    navigateToDetail(it)
                }
            }
        }
    }

    private val categoryTypeClassAdapter: CategoryRoundedCourseListAdapter by lazy {
        CategoryRoundedCourseListAdapter(viewModel) {
            viewModel.getCourse(null, it.nameCategory.lowercase())
        }
    }

    private fun navigateToDetail(course: CourseViewParam) {
        DetailClassActivity.startActivity(requireContext(), course)
    }

    fun updateViewBasedOnCategory(selectedCategory: String?) {
        viewModel.getCourse(selectedCategory, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showCourseType()
        showCategoryType()
        fetchData()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.tvFilter.setOnClickListener {
            val intent = Intent(requireContext(), FilterActivity::class.java)
            startActivityForResult(intent, FILTER_REQUEST_CODE)
        }

        binding.clSearchBar.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val FILTER_REQUEST_CODE = 123
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILTER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedCategory = data?.getStringExtra("selectedCategory")
            updateViewBasedOnCategory(selectedCategory)
        }
    }

    private fun showCategoryType() {
        binding.rvCategoryType.adapter = categoryTypeClassAdapter
        binding.rvCategoryType.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        viewModel.categoriesTypeClass.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.rvCategoryType.isVisible = true
                    binding.rvCategoryType.adapter = categoryTypeClassAdapter
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryType.root.isVisible = false
                    binding.layoutStateCategoryType.tvError.isVisible = false
                    binding.layoutStateCategoryType.pbLoading.isVisible = false
                    binding.layoutStateCategoryType.clDataEmpty.isVisible = false
                    binding.layoutStateCategoryType.tvDataEmpty.isVisible = false
                    binding.layoutStateCategoryType.ivDataEmpty.isVisible = false
                    binding.rvCategoryType.apply {
                        binding.rvCategoryType.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = categoryTypeClassAdapter
                    }
                    result.payload?.let { categoriesType ->
                        categoryTypeClassAdapter.setData(categoriesType)
                    }
                },
                doOnLoading = {
                    binding.rvCategoryType.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = true
                    binding.layoutStateCategoryType.root.isVisible = false
                    binding.layoutStateCategoryType.tvError.isVisible = false
                    binding.layoutStateCategoryType.pbLoading.isVisible = false
                    binding.layoutStateCategoryType.clDataEmpty.isVisible = false
                    binding.layoutStateCategoryType.tvDataEmpty.isVisible = false
                    binding.layoutStateCategoryType.ivDataEmpty.isVisible = false
                },
                doOnError = {
                    binding.rvCategoryType.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryType.root.isVisible = true
                    binding.layoutStateCategoryType.tvError.isVisible = true
                    binding.layoutStateCategoryType.tvError.text = it.exception?.message
                    binding.layoutStateCategoryType.pbLoading.isVisible = false
                    binding.layoutStateCategoryType.clDataEmpty.isVisible = false
                    binding.layoutStateCategoryType.tvDataEmpty.isVisible = false
                    binding.layoutStateCategoryType.ivDataEmpty.isVisible = false
                },
                doOnEmpty = {
                    binding.rvCategoryType.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryType.root.isVisible = false
                    binding.layoutStateCategoryType.tvError.isVisible = false
                    binding.layoutStateCategoryType.pbLoading.isVisible = false
                    binding.layoutStateCategoryType.clDataEmpty.isVisible = true
                    binding.layoutStateCategoryType.tvDataEmpty.isVisible = true
                    binding.layoutStateCategoryType.ivDataEmpty.isVisible = false
                }
            )
        }
    }

    private fun showCourseType() {
        binding.rvCourse.adapter = typeCourseAdapter
        binding.rvCourse.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        viewModel.course.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnLoading = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseLinear.isVisible = true
                    binding.layoutStateCourse.root.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.clDataEmpty.isVisible = false
                    binding.layoutStateCourse.tvDataEmpty.isVisible = false
                    binding.layoutStateCourse.ivDataEmpty.isVisible = false
                },
                doOnSuccess = { result ->
                    binding.rvCourse.isVisible = true
                    binding.rvCourse.adapter = typeCourseAdapter
                    binding.shimmerCourseLinear.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.clDataEmpty.isVisible = false
                    binding.layoutStateCourse.tvDataEmpty.isVisible = false
                    binding.layoutStateCourse.ivDataEmpty.isVisible = false
                    result.payload?.let { data ->
                        typeCourseAdapter.setData(data)
                    }
                },
                doOnError = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseLinear.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.tvError.isVisible = true
                    binding.layoutStateCourse.tvError.text = it.exception?.message
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.clDataEmpty.isVisible = false
                    binding.layoutStateCourse.tvDataEmpty.isVisible = false
                    binding.layoutStateCourse.ivDataEmpty.isVisible = false
                },
                doOnEmpty = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseLinear.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.clDataEmpty.isVisible = true
                    binding.layoutStateCourse.tvDataEmpty.isVisible = true
                    binding.layoutStateCourse.ivDataEmpty.isVisible = false
                }
            )
        }
    }

    private fun showDialog() {
        val binding: DialogNonLoginBinding = DialogNonLoginBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext(), 0).create()

        dialog.apply {
            setView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()

        binding.clSignUp.setOnClickListener {
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchData() {
        viewModel.getCourse()
        viewModel.getCategoriesTypeClass()
        viewModel.checkLogin()
    }
}
