package com.kelompoksatuandsatu.preducation.presentation.feature.classProgress

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.databinding.FragmentProgressClassBinding
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.progress.CourseProgressItemClass
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryCourseListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryRoundedListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.classprogress.CourseProgressListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.home.SeeAllPopularCoursesActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgressClassFragment : Fragment() {

    private lateinit var binding: FragmentProgressClassBinding

    private val searchView: SearchView by lazy {
        binding.clSearchBar.findViewById(R.id.sv_search)
    }

    private val viewModel: ProgressClassViewModel by viewModel()

    private val categoryCourseAdapter: CategoryCourseListAdapter by lazy {
        CategoryCourseListAdapter { selectedCategory ->
            navigateToSeeAllActivity(selectedCategory)
        }
    }

    private fun navigateToSeeAllActivity(selectedCategory: CategoryClass) {
        val intent = Intent(requireContext(), SeeAllPopularCoursesActivity::class.java)
        intent.putExtra("CATEGORY_NAME", selectedCategory.name)
        startActivity(intent)
    }

    private val categoryProgressAdapter: CategoryRoundedListAdapter by lazy {
        CategoryRoundedListAdapter(viewModel) {
            viewModel.getCourseProgress(it.nameCategory)
        }
    }

    private val progressCourseAdapter: CourseProgressListAdapter by lazy {
        CourseProgressListAdapter {
            viewModel.isUserLogin.observe(viewLifecycleOwner) { isLogin ->
                if (!isLogin) {
                    showDialog()
                } else {
                    navigateCourseProgressToDetail(it)
                }
            }
        }
    }

    private val searchQueryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                progressCourseAdapter.filter(it)
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    private fun navigateCourseProgressToDetail(course: CourseProgressItemClass) {
        DetailClassActivity.startActivityProgress(requireContext(), course)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
        showCategoryCourse()
        showCourse()
        showCategoryProgress()
        setOnClickListener()
        searchView.setOnQueryTextListener(searchQueryListener)
    }
    private fun fetchData() {
        viewModel.getCategoriesClass()
        viewModel.getCategoriesProgress()
        viewModel.getCourseProgress()
        viewModel.checkLogin()
    }

    private fun setOnClickListener() {
        binding.tvNavToSeeAllTitleCategory.setOnClickListener {
            SeeAllPopularCoursesActivity.startActivity(requireContext())
        }

        binding.ivToSeeAll.setOnClickListener {
            SeeAllPopularCoursesActivity.startActivity(requireContext())
        }

        binding.clSearchBar.findViewById<ImageView>(R.id.iv_search).setOnClickListener {
            val query = searchView.query.toString()
            progressCourseAdapter.filter(query)
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

    private fun showCategoryProgress() {
        binding.rvCategoryProgress.adapter = categoryProgressAdapter
        binding.rvCategoryProgress.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        viewModel.categoriesProgress.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.rvCategoryProgress.isVisible = true
                    binding.layoutStateCategoryProgress.tvError.isVisible = false
                    binding.shLoadingCategoryProgress.isVisible = false

                    result.payload?.let { categoriesProgress ->
                        categoryProgressAdapter.setData(categoriesProgress)
                    }
                },
                doOnLoading = {
                    binding.layoutStateCategoryProgress.root.isVisible = true
                    binding.shLoadingCategoryProgress.isVisible = true
                    binding.rvCategoryProgress.isVisible = false
                },
                doOnError = {
                    binding.layoutStateCategoryProgress.root.isVisible = true
                    binding.shLoadingCategoryProgress.isVisible = false
                    binding.layoutStateCategoryProgress.tvError.isVisible = true
                    binding.layoutStateCategoryProgress.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCategoryProgress.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutStateCategoryProgress.root.isVisible = true
                    binding.shLoadingCategoryProgress.isVisible = false
                    binding.layoutStateCategoryProgress.tvError.isVisible = true
                    binding.layoutStateCategoryProgress.tvError.text = getString(R.string.empty_progress_category)
                    binding.rvCategoryProgress.isVisible = false
                }
            )
        }
    }

    private fun showCourse() {
        binding.rvProgressCourse.adapter = progressCourseAdapter
        binding.rvProgressCourse.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        viewModel.courseProgress.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.rvProgressCourse.isVisible = true
                    binding.layoutStateCourseProgress.tvError.isVisible = false
                    binding.shLoadingCourseProgress.isVisible = false

                    result.payload?.let { courseProgress ->
                        progressCourseAdapter.setData(courseProgress)
                    }
                },
                doOnLoading = {
                    binding.layoutStateCourseProgress.root.isVisible = true
                    binding.shLoadingCourseProgress.isVisible = true
                    binding.rvProgressCourse.isVisible = false
                },
                doOnError = {
                    binding.layoutStateCourseProgress.root.isVisible = true
                    binding.shLoadingCourseProgress.isVisible = false
                    binding.layoutStateCourseProgress.tvError.isVisible = true
                    binding.layoutStateCourseProgress.tvError.text = it.exception?.message.orEmpty()
                    binding.rvProgressCourse.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutStateCourseProgress.root.isVisible = true
                    binding.shLoadingCourseProgress.isVisible = false
                    binding.layoutStateCourseProgress.tvError.isVisible = true
                    binding.layoutStateCourseProgress.tvError.text = getString(R.string.empty_class_progress)
                    binding.rvProgressCourse.isVisible = false
                }
            )
        }

        viewModel.isUserLogin.observe(viewLifecycleOwner) { isLogin ->
            if (!isLogin) {
                showDialog()
            }
        }
    }

    private fun showCategoryCourse() {
        binding.rvCategoryCourse.adapter = categoryCourseAdapter
        binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireContext(), 4)

        viewModel.categoriesClass.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.rvCategoryCourse.isVisible = true
                    binding.layoutStateCategoryCourse.tvError.isVisible = false
                    binding.shLoadingCategoryCourse.isVisible = false

                    result.payload?.let { categoriesCourse ->
                        categoryCourseAdapter.setData(categoriesCourse)
                    }
                },
                doOnLoading = {
                    binding.layoutStateCategoryCourse.root.isVisible = true
                    binding.shLoadingCategoryCourse.isVisible = true
                    binding.rvCategoryCourse.isVisible = false
                },
                doOnError = {
                    binding.layoutStateCategoryCourse.root.isVisible = true
                    binding.shLoadingCategoryCourse.isVisible = false
                    binding.layoutStateCategoryCourse.tvError.isVisible = true
                    binding.layoutStateCategoryCourse.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCategoryCourse.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutStateCategoryCourse.root.isVisible = true
                    binding.shLoadingCategoryCourse.isVisible = false
                    binding.layoutStateCategoryCourse.tvError.isVisible = true
                    binding.layoutStateCategoryCourse.tvError.text = getString(R.string.you_need_to_login)
                    binding.rvCategoryCourse.isVisible = false
                }
            )
        }
    }
}
