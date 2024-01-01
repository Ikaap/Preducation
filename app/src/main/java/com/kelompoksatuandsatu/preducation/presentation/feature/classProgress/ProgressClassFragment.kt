package com.kelompoksatuandsatu.preducation.presentation.feature.classProgress

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.FragmentProgressClassBinding
import com.kelompoksatuandsatu.preducation.databinding.LayoutDialogAccessFeatureBinding
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.progress.CourseProgressItemClass
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryCourseListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryRoundedListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.classprogress.CourseProgressListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.home.SeeAllPopularCoursesActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgressClassFragment : Fragment() {

    private lateinit var binding: FragmentProgressClassBinding

    private val viewModel: ProgressClassViewModel by viewModel()

    private val assetWrapper: AssetWrapper by inject()

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
            viewModel.getCourseProgress(it.nameCategory.lowercase())
        }
    }

    private val progressCourseAdapter: CourseProgressListAdapter by lazy {
        CourseProgressListAdapter(assetWrapper) {
            navigateCourseProgressToDetail(it)
        }
    }

    private fun navigateCourseProgressToDetail(course: CourseProgressItemClass) {
        DetailClassActivity.startActivityProgress(requireContext(), course)
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.class_navigate_to_home)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.checkLogin()

        viewModel.isUserLogin.observe(viewLifecycleOwner) { isLogin ->
            if (!isLogin) {
                showDialogNotification()
                navigateToHome()
            }
        }

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
    }

    private fun fetchData() {
        viewModel.getCategoriesClass()
        viewModel.getCategoriesProgress()
        viewModel.getCourseProgress()
    }

    private fun setOnClickListener() {
        binding.tvNavToSeeAllTitleCategory.setOnClickListener {
            SeeAllPopularCoursesActivity.startActivity(requireContext())
        }

        binding.ivToSeeAll.setOnClickListener {
            SeeAllPopularCoursesActivity.startActivity(requireContext())
        }
    }

    private fun showDialogNotification() {
        val binding: LayoutDialogAccessFeatureBinding =
            LayoutDialogAccessFeatureBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext(), 0).create()

        dialog.apply {
            setView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()

        binding.clSignIn.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
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
                    binding.shLoadingCategoryProgress.isVisible = false
                    binding.layoutStateCategoryProgress.root.isVisible = false
                    binding.layoutStateCategoryProgress.tvError.isVisible = false
                    binding.layoutStateCategoryProgress.pbLoading.isVisible = false
                    binding.layoutStateCategoryProgress.clErrorState.isVisible = false
                    binding.layoutStateCategoryProgress.tvErrorState.isVisible = false
                    binding.layoutStateCategoryProgress.ivErrorState.isVisible = false
                    binding.rvCategoryProgress.apply {
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = categoryProgressAdapter
                    }
                    result.payload?.let { categoriesProgress ->
                        categoryProgressAdapter.setData(categoriesProgress)
                    }
                },
                doOnLoading = {
                    binding.rvCategoryProgress.isVisible = false
                    binding.shLoadingCategoryProgress.isVisible = true
                    binding.layoutStateCategoryProgress.root.isVisible = false
                    binding.layoutStateCategoryProgress.tvError.isVisible = false
                    binding.layoutStateCategoryProgress.pbLoading.isVisible = false
                    binding.layoutStateCategoryProgress.clErrorState.isVisible = false
                    binding.layoutStateCategoryProgress.tvErrorState.isVisible = false
                    binding.layoutStateCategoryProgress.ivErrorState.isVisible = false
                },
                doOnError = {
                    binding.rvCategoryProgress.isVisible = false
                    binding.shLoadingCategoryProgress.isVisible = false
                    binding.layoutStateCategoryProgress.root.isVisible = true
                    binding.layoutStateCategoryProgress.tvError.isVisible = true
                    binding.layoutStateCategoryProgress.tvError.text = it.exception?.message
                    binding.layoutStateCategoryProgress.pbLoading.isVisible = false
                    binding.layoutStateCategoryProgress.clErrorState.isVisible = false
                    binding.layoutStateCategoryProgress.tvErrorState.isVisible = false
                    binding.layoutStateCategoryProgress.ivErrorState.isVisible = false

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorCategoriesType()?.success == false) {
                            binding.layoutStateCategoryProgress.tvError.text =
                                it.exception.getParsedErrorCategories()?.message
                        }
                    }
                },
                doOnEmpty = {
                    binding.rvCategoryProgress.isVisible = false
                    binding.shLoadingCategoryProgress.isVisible = false
                    binding.layoutStateCategoryProgress.root.isVisible = false
                    binding.layoutStateCategoryProgress.tvError.isVisible = false
                    binding.layoutStateCategoryProgress.pbLoading.isVisible = false
                    binding.layoutStateCategoryProgress.clErrorState.isVisible = true
                    binding.layoutStateCategoryProgress.tvErrorState.isVisible = true
                    binding.layoutStateCategoryProgress.ivErrorState.isVisible = false
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
                    binding.shLoadingCourseProgress.isVisible = false
                    binding.layoutStateCourseProgress.tvError.isVisible = false
                    binding.layoutStateCourseProgress.pbLoading.isVisible = false
                    binding.layoutStateCourseProgress.clErrorState.isVisible = false
                    binding.layoutStateCourseProgress.tvErrorState.isVisible = false
                    binding.layoutStateCourseProgress.ivErrorState.isVisible = false
                    result.payload?.let { courseProgress ->
                        progressCourseAdapter.setData(courseProgress)
                    }
                },
                doOnLoading = {
                    binding.rvProgressCourse.isVisible = false
                    binding.shLoadingCourseProgress.isVisible = true
                    binding.layoutStateCourseProgress.root.isVisible = false
                    binding.layoutCommonState.root.isVisible = false
                    binding.layoutStateCourseProgress.tvError.isVisible = false
                    binding.layoutStateCourseProgress.pbLoading.isVisible = false
                    binding.layoutStateCourseProgress.clErrorState.isVisible = false
                    binding.layoutStateCourseProgress.tvErrorState.isVisible = false
                    binding.layoutStateCourseProgress.ivErrorState.isVisible = false
                },
                doOnError = {
                    binding.rvProgressCourse.isVisible = false
                    binding.shLoadingCourseProgress.isVisible = false
                    binding.layoutStateCourseProgress.root.isVisible = true
                    binding.layoutStateCourseProgress.tvError.isVisible = true
                    binding.layoutStateCourseProgress.tvError.text = it.exception?.message
                    binding.layoutStateCourseProgress.pbLoading.isVisible = false

                    if (it.exception is ApiException) {
                        binding.layoutCommonState.root.isVisible = true
                        if (it.exception.getParsedErrorProgressClass()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clErrorState.isGone = false
                                binding.layoutCommonState.ivErrorState.isGone = false
                                binding.layoutCommonState.tvErrorState.isGone = false
                                binding.layoutCommonState.tvErrorState.text = getString(R.string.text_sorry_there_s_an_error_on_the_server)
                                binding.layoutCommonState.ivErrorState.setImageResource(R.drawable.img_server_error)
                            } else if (it.exception.getParsedErrorProgressClass()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorProgressClass()?.message
                            }
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(requireContext())) {
                            binding.layoutCommonState.root.isVisible = true
                            binding.layoutCommonState.clErrorState.isGone = false
                            binding.layoutCommonState.ivErrorState.isGone = false
                            binding.layoutCommonState.tvErrorState.isGone = false
                            binding.layoutCommonState.tvErrorState.text = getString(R.string.text_no_internet_connection)
                            binding.layoutCommonState.ivErrorState.setImageResource(R.drawable.img_no_connection)
                        }
                    }
                },
                doOnEmpty = {
                    binding.rvProgressCourse.isVisible = false
                    binding.shLoadingCourseProgress.isVisible = false
                    binding.layoutStateCourseProgress.root.isVisible = true
                    binding.layoutStateCourseProgress.tvError.isVisible = true
                    binding.layoutStateCourseProgress.pbLoading.isVisible = false
                    binding.layoutStateCourseProgress.clErrorState.isVisible = true
                    binding.layoutStateCourseProgress.tvErrorState.isVisible = true
                    binding.layoutStateCourseProgress.tvErrorState.text = getString(R.string.text_you_don_t_have_any_classes_yet)
                    binding.layoutStateCourseProgress.ivErrorState.isVisible = true
                }
            )
        }
    }

    private fun showCategoryCourse() {
        binding.rvCategoryCourse.adapter = categoryCourseAdapter
        binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireContext(), 4)

        viewModel.categoriesClass.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.rvCategoryCourse.isVisible = true
                    binding.shLoadingCategoryCourse.isVisible = false
                    binding.layoutStateCategoryCourse.tvError.isVisible = false
                    binding.layoutStateCategoryCourse.pbLoading.isVisible = false
                    binding.layoutStateCategoryCourse.clErrorState.isVisible = false
                    binding.layoutStateCategoryCourse.tvErrorState.isVisible = false
                    binding.layoutStateCategoryCourse.ivErrorState.isVisible = false
                    result.payload?.let { categoriesCourse ->
                        categoryCourseAdapter.setData(categoriesCourse)
                    }
                },
                doOnLoading = {
                    binding.rvCategoryCourse.isVisible = false
                    binding.shLoadingCategoryCourse.isVisible = true
                    binding.layoutStateCategoryCourse.root.isVisible = false
                    binding.layoutStateCategoryCourse.tvError.isVisible = false
                    binding.layoutStateCategoryCourse.pbLoading.isVisible = false
                    binding.layoutStateCategoryCourse.clErrorState.isVisible = false
                    binding.layoutStateCategoryCourse.tvErrorState.isVisible = false
                    binding.layoutStateCategoryCourse.ivErrorState.isVisible = false
                },
                doOnError = {
                    binding.rvCategoryCourse.isVisible = false
                    binding.shLoadingCategoryCourse.isVisible = false
                    binding.layoutStateCategoryCourse.root.isVisible = true
                    binding.layoutStateCategoryCourse.tvError.isVisible = true
                    binding.layoutStateCategoryCourse.tvError.text = it.exception?.message
                    binding.layoutStateCategoryCourse.pbLoading.isVisible = false
                    binding.layoutStateCategoryCourse.clErrorState.isVisible = false
                    binding.layoutStateCategoryCourse.tvErrorState.isVisible = false
                    binding.layoutStateCategoryCourse.ivErrorState.isVisible = false

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorCategories()?.success == false) {
                            binding.layoutStateCategoryProgress.tvError.text =
                                it.exception.getParsedErrorCategories()?.message
                        }
                    }
                },
                doOnEmpty = {
                    binding.rvCategoryCourse.isVisible = false
                    binding.shLoadingCategoryCourse.isVisible = false
                    binding.layoutStateCategoryCourse.root.isVisible = true
                    binding.layoutStateCategoryCourse.tvError.isVisible = false
                    binding.layoutStateCategoryCourse.pbLoading.isVisible = false
                    binding.layoutStateCategoryCourse.clErrorState.isVisible = true
                    binding.layoutStateCategoryCourse.tvErrorState.isVisible = true
                    binding.layoutStateCategoryCourse.ivErrorState.isVisible = false
                }
            )
        }
    }
}
