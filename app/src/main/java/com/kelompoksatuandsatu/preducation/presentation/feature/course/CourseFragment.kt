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
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.databinding.FragmentCourseBinding
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CourseLinearListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryRoundedCourseListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.filter.FilterActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
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
            viewModel.getCourseTopic(it.nameCategory.lowercase())
        }
    }

    private val searchView: SearchView by lazy {
        binding.clSearchBar.findViewById(R.id.sv_search)
    }

    private val searchQueryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                typeCourseAdapter.filter(it)
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    private fun navigateToDetail(course: CourseViewParam) {
        DetailClassActivity.startActivity(requireContext(), course)
    }

    fun updateViewBasedOnCategory(selectedCategory: String?) {
        viewModel.getCourseTopic(selectedCategory)
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
        searchView.setOnQueryTextListener(searchQueryListener)
    }

    private fun setOnClickListener() {
        binding.tvFilter.setOnClickListener {
            val intent = Intent(requireContext(), FilterActivity::class.java)
            startActivityForResult(intent, FILTER_REQUEST_CODE)
        }

        binding.clSearchBar.findViewById<ImageView>(R.id.iv_search).setOnClickListener {
            val query = searchView.query.toString()
            typeCourseAdapter.filter(query)
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
                    binding.layoutStateCategoryType.tvError.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = false

                    result.payload?.let { categoriesType ->
                        categoryTypeClassAdapter.setData(categoriesType)
                    }
                },
                doOnLoading = {
                    binding.layoutStateCategoryType.root.isVisible = true
                    binding.shimmerCategoryRounded.isVisible = true
                    binding.rvCategoryType.isVisible = false
                },
                doOnError = {
                    binding.layoutStateCategoryType.root.isVisible = true
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryType.tvError.isVisible = true
                    binding.layoutStateCategoryType.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCategoryType.isVisible = false
                },
                doOnEmpty = {
                    binding.rvCategoryType.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryType.root.isVisible = true
                    binding.layoutStateCategoryType.tvError.isVisible = false
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
                    binding.layoutStateCourse.root.isVisible = true
                    binding.shimmerCourseLinear.isVisible = true
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.rvCourse.isVisible = false
                },
                doOnSuccess = { result ->
                    binding.layoutStateCourse.root.isVisible = false
                    binding.rvCourse.isVisible = true
                    binding.shimmerCourseLinear.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    result.payload?.let { data ->
                        typeCourseAdapter.setData(data)
                    }
                },
                doOnError = {
                    binding.layoutStateCourse.root.isVisible = true
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseLinear.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = true
                },
                doOnEmpty = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseLinear.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.tvError.isVisible = false
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
        viewModel.getCourseTopic()
        viewModel.getCategoriesTypeClass()
        viewModel.checkLogin()
    }
}
