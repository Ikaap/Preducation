package com.kelompoksatuandsatu.preducation.presentation.feature.home

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
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.databinding.FragmentHomeBinding
import com.kelompoksatuandsatu.preducation.model.Course
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CategoryCourseListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CategoryCourseRoundedListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CourseCardListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val categoryCourseAdapter: CategoryCourseListAdapter by lazy {
        CategoryCourseListAdapter {
            showSuccessDialog()
        }
    }

    private val categoryCoursePopularAdapter: CategoryCourseRoundedListAdapter by lazy {
        CategoryCourseRoundedListAdapter {
            viewModel.getCourse(it.name)
        }
    }

    private val popularCourseAdapter: CourseCardListAdapter by lazy {
        CourseCardListAdapter(AdapterLayoutMenu.HOME) {
            showSuccessDialog()
            navigateToDetail(it)
        }
    }

    private val viewModel: HomeViewModel by viewModel()

    private fun navigateToDetail(course: Course) {
        DetailClassActivity.startActivity(requireContext(), course)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        getData()
        observeData()
    }

    private fun setOnClickListener() {
        binding.rvPopularCourse.setOnClickListener {
            showSuccessDialog()
        }

        binding.tvNavToSeeAllTitleCategory.setOnClickListener {
            SeeAllPopularCoursesActivity.startActivity(requireContext())
        }
        binding.ivToSeeAll.setOnClickListener {
            SeeAllPopularCoursesActivity.startActivity(requireContext())
        }
    }

    private fun getData() {
        viewModel.getCategoriesClass()
        viewModel.getCategoriesClassPopular()
        viewModel.getCourse()
    }

    private fun observeData() {
        viewModel.categoriesClass.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutStateCategoryCourse.root.isVisible = false
                    binding.layoutStateCategoryCourse.pbLoading.isVisible = false
                    binding.layoutStateCategoryCourse.tvError.isVisible = false
                    binding.rvCategoryCourse.apply {
                        binding.rvCategoryCourse.apply {
                            isVisible = true
                            adapter = categoryCourseAdapter
                        }
                    }
                    it.payload?.let { data ->
                        categoryCourseAdapter.setData(data)
                    }
                },
                doOnLoading = {
                    binding.layoutStateCategoryCourse.root.isVisible = true
                    binding.layoutStateCategoryCourse.pbLoading.isVisible = true
                    binding.layoutStateCategoryCourse.tvError.isVisible = false
                    binding.rvCategoryCourse.isVisible = false
                },
                doOnError = {
                    binding.layoutStateCategoryCourse.root.isVisible = true
                    binding.layoutStateCategoryCourse.pbLoading.isVisible = false
                    binding.layoutStateCategoryCourse.tvError.isVisible = true
                    binding.layoutStateCategoryCourse.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCategoryCourse.isVisible = false
                }
            )
        }

        viewModel.categoriesClassPopular.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvCategoryPopular.apply {
                        binding.rvCategoryPopular.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = categoryCoursePopularAdapter
                    }
                    it.payload?.let { data ->
                        categoryCoursePopularAdapter.setData(data)
                    }
                }
            )
        }

        viewModel.course.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutStatePopularCourse.root.isVisible = false
                    binding.layoutStatePopularCourse.pbLoading.isVisible = false
                    binding.layoutStatePopularCourse.tvError.isVisible = false
                    binding.rvPopularCourse.apply {
                        binding.rvPopularCourse.apply {
                            isVisible = true
                            adapter = popularCourseAdapter
                        }
                        adapter = popularCourseAdapter
                    }
                    it.payload?.let { data ->
                        popularCourseAdapter.setData(data)
                    }
                },
                doOnLoading = {
                    binding.layoutStatePopularCourse.root.isVisible = true
                    binding.layoutStatePopularCourse.pbLoading.isVisible = true
                    binding.layoutStatePopularCourse.tvError.isVisible = false
                    binding.rvPopularCourse.isVisible = false
                },
                doOnError = {
                    binding.layoutStatePopularCourse.root.isVisible = true
                    binding.layoutStatePopularCourse.pbLoading.isVisible = false
                    binding.layoutStatePopularCourse.tvError.isVisible = true
                    binding.layoutStatePopularCourse.tvError.text = it.exception?.message.orEmpty()
                    binding.rvPopularCourse.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutStatePopularCourse.root.isVisible = true
                    binding.layoutStatePopularCourse.pbLoading.isVisible = false
                    binding.layoutStatePopularCourse.tvError.isVisible = true
                    binding.layoutStatePopularCourse.tvError.text =
                        resources.getString(R.string.popular_course_not_found)
                    binding.rvPopularCourse.isVisible = false
                }
            )
        }
    }

    private fun showSuccessDialog() {
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
}
