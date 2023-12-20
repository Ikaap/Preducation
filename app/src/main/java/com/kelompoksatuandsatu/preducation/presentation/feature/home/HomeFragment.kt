package com.kelompoksatuandsatu.preducation.presentation.feature.home

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
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.databinding.FragmentHomeBinding
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryCourseListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryRoundedHomeListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.CourseCardListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val searchView: SearchView by lazy {
        binding.clSearchBar.findViewById(R.id.sv_search)
    }

    private val searchQueryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                popularCourseAdapter.filter(it)
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

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

    private val categoryCoursePopularAdapter: CategoryRoundedHomeListAdapter by lazy {
        CategoryRoundedHomeListAdapter(viewModel) {
            viewModel.getCourse(it.name)
        }
    }

    private val popularCourseAdapter: CourseCardListAdapter by lazy {
        CourseCardListAdapter(AdapterLayoutMenu.HOME) {
            viewModel.isUserLogin.observe(viewLifecycleOwner) { isLogin ->
                if (!isLogin) {
                    showDialog()
                } else {
                    navigateToDetail(it)
                }
            }
        }
    }

    private val viewModel: HomeViewModel by viewModel()

    private fun navigateToDetail(course: CourseViewParam) {
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
        searchView.setOnQueryTextListener(searchQueryListener)
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
            popularCourseAdapter.filter(query)
        }
    }

    private fun getData() {
        viewModel.getCategoriesClass()
        viewModel.getCategoriesClassPopular()
        viewModel.getCourse()
        viewModel.checkLogin()
    }

    private fun observeData() {
        viewModel.categoriesClass.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvCategoryCourse.isVisible = true
                    binding.shimmerCategoryCircle.isVisible = false
                    binding.layoutStateCategoryCircle.root.isGone = true
                    binding.layoutStateCategoryCircle.ivDataEmpty.isGone = true
                    binding.layoutStateCategoryCircle.tvError.isGone = true
                    binding.layoutStateCategoryCircle.tvDataEmpty.isGone = true
                    binding.rvCategoryCourse.apply {
                        isVisible = true
                        adapter = categoryCourseAdapter
                    }
                    it.payload?.let { data ->
                        categoryCourseAdapter.setData(data)
                    }
                },
                doOnLoading = {
                    binding.rvCategoryCourse.isVisible = false
                    binding.shimmerCategoryCircle.isVisible = true
                    binding.layoutStateCategoryCircle.root.isGone = true
                    binding.layoutStateCategoryCircle.ivDataEmpty.isGone = true
                    binding.layoutStateCategoryCircle.tvError.isGone = true
                    binding.layoutStateCategoryCircle.tvDataEmpty.isGone = true
                },
                doOnEmpty = {
                    binding.rvCategoryCourse.isVisible = false
                    binding.shimmerCategoryCircle.isVisible = false
                    binding.layoutStateCategoryCircle.root.isGone = false
                    binding.layoutStateCategoryCircle.ivDataEmpty.isGone = false
                    binding.layoutStateCategoryCircle.tvError.isGone = true
                    binding.layoutStateCategoryCircle.tvDataEmpty.isGone = false
                },
                doOnError = {
                    binding.rvCategoryCourse.isVisible = false
                    binding.shimmerCategoryCircle.isVisible = false
                    binding.layoutStateCategoryCircle.root.isGone = false
                    binding.layoutStateCategoryCircle.ivDataEmpty.isGone = true
                    binding.layoutStateCategoryCircle.tvError.isGone = false
                    binding.layoutStateCategoryCircle.tvError.text = it.exception?.message
                    binding.layoutStateCategoryCircle.tvDataEmpty.isGone = true
                }
            )
        }

        viewModel.categoriesClassPopular.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvCategoryPopular.isVisible = true
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryRounded.root.isGone = true
                    binding.layoutStateCategoryRounded.ivDataEmpty.isGone = true
                    binding.layoutStateCategoryRounded.tvError.isGone = true
                    binding.layoutStateCategoryRounded.tvDataEmpty.isGone = true
                    binding.rvCategoryPopular.apply {
                        isVisible = true
                        adapter = categoryCoursePopularAdapter
                    }
                    it.payload?.let { data ->
                        categoryCoursePopularAdapter.setData(data)
                    }
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
                },
                doOnLoading = {
                    binding.rvCategoryPopular.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = true
                    binding.layoutStateCategoryRounded.root.isGone = true
                    binding.layoutStateCategoryRounded.ivDataEmpty.isGone = true
                    binding.layoutStateCategoryRounded.tvError.isGone = true
                    binding.layoutStateCategoryRounded.tvDataEmpty.isGone = true
                },
                doOnEmpty = {
                    binding.rvCategoryPopular.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryRounded.root.isGone = false
                    binding.layoutStateCategoryRounded.ivDataEmpty.isGone = false
                    binding.layoutStateCategoryRounded.tvError.isGone = true
                    binding.layoutStateCategoryRounded.tvDataEmpty.isGone = false
                },
                doOnError = {
                    binding.rvCategoryPopular.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryRounded.root.isGone = false
                    binding.layoutStateCategoryRounded.ivDataEmpty.isGone = true
                    binding.layoutStateCategoryRounded.tvError.isGone = false
                    binding.layoutStateCategoryRounded.tvError.text = it.exception?.message
                    binding.layoutStateCategoryRounded.tvDataEmpty.isGone = true
                }
            )
        }

        viewModel.coursePopular.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvPopularCourse.isVisible = true
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isGone = true
                    binding.layoutStateCoursePopular.ivDataEmpty.isGone = true
                    binding.layoutStateCoursePopular.tvError.isGone = true
                    binding.layoutStateCoursePopular.tvDataEmpty.isGone = true
                    binding.rvPopularCourse.apply {
                        isVisible = true
                        adapter = popularCourseAdapter
                    }
                    it.payload?.let { data ->
                        popularCourseAdapter.setData(data)
                    }
                },
                doOnLoading = {
                    binding.rvPopularCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = true
                    binding.layoutStateCoursePopular.root.isGone = true
                    binding.layoutStateCoursePopular.ivDataEmpty.isGone = true
                    binding.layoutStateCoursePopular.tvError.isGone = true
                    binding.layoutStateCoursePopular.tvDataEmpty.isGone = true
                },
                doOnEmpty = {
                    binding.rvPopularCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isGone = false
                    binding.layoutStateCoursePopular.ivDataEmpty.isGone = false
                    binding.layoutStateCoursePopular.tvError.isGone = true
                    binding.layoutStateCoursePopular.tvDataEmpty.isGone = false
                },
                doOnError = {
                    binding.rvPopularCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isGone = false
                    binding.layoutStateCoursePopular.ivDataEmpty.isGone = true
                    binding.layoutStateCoursePopular.tvError.isGone = false
                    binding.layoutStateCoursePopular.tvError.text = it.exception?.message
                    binding.layoutStateCoursePopular.tvDataEmpty.isGone = true
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
}
