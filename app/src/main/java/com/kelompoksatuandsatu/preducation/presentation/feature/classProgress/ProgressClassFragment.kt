package com.kelompoksatuandsatu.preducation.presentation.feature.classProgress

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.databinding.FragmentProgressClassBinding
import com.kelompoksatuandsatu.preducation.model.progress.CourseProgressItemClass
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryCourseListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryRoundedListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.classprogress.CourseProgressListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.home.SeeAllPopularCoursesActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgressClassFragment : Fragment() {

    private lateinit var binding: FragmentProgressClassBinding

    private val viewModel: ProgressClassViewModel by viewModel()

    private val categoryCourseAdapter: CategoryCourseListAdapter by lazy {
        CategoryCourseListAdapter {
            viewModel.getCourseProgress(it.name)
        }
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

    private val searchView: SearchView by lazy {
        binding.clSearchBar.findViewById(R.id.cv_search)
    }

    private val searchQueryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            progressCourseAdapter.filter(newText)
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
    ): View {
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
//        searchView.setOnQueryTextListener(searchQueryListener)
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
                    binding.layoutStateCategoryProgress.pbLoading.isVisible = false

                    result.payload?.let { categoriesProgress ->
                        categoryProgressAdapter.setData(categoriesProgress)
                    }
                },
                doOnLoading = {
                    binding.layoutStateCategoryProgress.root.isVisible = true
                    binding.layoutStateCategoryProgress.pbLoading.isVisible = true
                    binding.rvCategoryProgress.isVisible = false
                },
                doOnError = {
                    binding.layoutStateCategoryProgress.root.isVisible = true
                    binding.layoutStateCategoryProgress.pbLoading.isVisible = false
                    binding.layoutStateCategoryProgress.tvError.isVisible = true
                    binding.layoutStateCategoryProgress.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCategoryProgress.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutStateCategoryProgress.root.isVisible = true
                    binding.layoutStateCategoryProgress.pbLoading.isVisible = false
                    binding.layoutStateCategoryProgress.tvError.isVisible = true
                    binding.layoutStateCategoryProgress.tvError.text = "Login dulu"
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
                doOnLoading = {
                    binding.layoutStateCourseProgress.root.isVisible = true
                    binding.layoutStateCourseProgress.pbLoading.isVisible = true
                    binding.layoutStateCourseProgress.tvError.isVisible = false
                    binding.rvProgressCourse.isVisible = false
                },
                doOnSuccess = { result ->
                    binding.layoutStateCourseProgress.root.isVisible = false
                    binding.rvProgressCourse.isVisible = true
                    binding.layoutStateCourseProgress.pbLoading.isVisible = false
                    binding.layoutStateCourseProgress.tvError.isVisible = false

                    result.payload?.let { data ->
                        progressCourseAdapter.setData(data)
                    }
                },
                doOnError = {
                    binding.layoutStateCourseProgress.root.isVisible = true
                    binding.rvProgressCourse.isVisible = false
                    binding.layoutStateCourseProgress.pbLoading.isVisible = false
                    binding.layoutStateCourseProgress.tvError.isVisible = true

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorProgressClass()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clServerError.isGone = false
                                binding.layoutCommonState.ivServerError.isGone = false
                                StyleableToast.makeText(
                                    requireContext(),
                                    "SERVER ERROR",
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorProgressClass()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorProgressClass()?.message
                                StyleableToast.makeText(
                                    requireContext(),
                                    it.exception.getParsedErrorProgressClass()?.message,
                                    R.style.failedtoast
                                ).show()
                            }
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(requireContext())) {
                            binding.layoutCommonState.clNoConnection.isGone = false
                            binding.layoutCommonState.ivNoConnection.isGone = false
                            StyleableToast.makeText(
                                requireContext(),
                                "tidak ada internet",
                                R.style.failedtoast
                            ).show()
                        }
                    }
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
                    binding.layoutStateCategoryCourse.pbLoading.isVisible = false

                    result.payload?.let { categoriesCourse ->
                        categoryCourseAdapter.setData(categoriesCourse)
                    }
                },
                doOnLoading = {
                    binding.layoutStateCategoryCourse.root.isVisible = true
                    binding.layoutStateCategoryCourse.pbLoading.isVisible = true
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
    }
}
