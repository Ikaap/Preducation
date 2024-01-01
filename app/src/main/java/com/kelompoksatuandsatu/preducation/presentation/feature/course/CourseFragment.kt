package com.kelompoksatuandsatu.preducation.presentation.feature.course

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.FragmentCourseBinding
import com.kelompoksatuandsatu.preducation.databinding.LayoutDialogAccessFeatureBinding
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.category.CategoryRoundedCourseListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.CourseLinearListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.filter.FilterFragment
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.search.SearchActivity
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseFragment : Fragment(), FilterFragment.OnFilterListener {

    private lateinit var binding: FragmentCourseBinding

    private val filterFragment: FilterFragment by lazy {
        FilterFragment()
    }

    private val viewModel: CourseViewModel by viewModel()

    private val assetWrapper: AssetWrapper by inject()

    private val typeCourseAdapter: CourseLinearListAdapter by lazy {
        CourseLinearListAdapter(AdapterLayoutMenu.COURSE, assetWrapper) {
            navigateToDetail(it)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.checkLogin()

        viewModel.isUserLogin.observe(viewLifecycleOwner) { isLogin ->
            if (!isLogin) {
                showDialogNotification()
                navigateToMain()
            }
        }
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun navigateToMain() {
        findNavController().navigate(R.id.course_navigate_to_home)
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
            filterFragment.setFilterListener(this)
            filterFragment.show(childFragmentManager, getString(R.string.text_filter))
        }

        binding.clSearchBar.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
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
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryType.root.isVisible = false
                    binding.layoutStateCategoryType.tvError.isVisible = false
                    binding.layoutStateCategoryType.pbLoading.isVisible = false
                    binding.layoutStateCategoryType.clErrorState.isVisible = false
                    binding.layoutStateCategoryType.tvErrorState.isVisible = false
                    binding.layoutStateCategoryType.ivErrorState.isVisible = false
                    binding.rvCategoryType.apply {
                        layoutManager = LinearLayoutManager(
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
                    binding.layoutStateCategoryType.clErrorState.isVisible = false
                    binding.layoutStateCategoryType.tvErrorState.isVisible = false
                    binding.layoutStateCategoryType.ivErrorState.isVisible = false
                },
                doOnError = {
                    binding.rvCategoryType.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryType.root.isVisible = true
                    binding.layoutStateCategoryType.tvError.isVisible = true
                    binding.layoutStateCategoryType.tvError.text = it.exception?.message
                    binding.layoutStateCategoryType.pbLoading.isVisible = false
                    binding.layoutStateCategoryType.clErrorState.isVisible = false
                    binding.layoutStateCategoryType.tvErrorState.isVisible = false
                    binding.layoutStateCategoryType.ivErrorState.isVisible = false

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorCategoriesType()?.success == false) {
                            binding.layoutStateCategoryType.tvError.text =
                                it.exception.getParsedErrorCategories()?.message
                        }
                    }
                },
                doOnEmpty = {
                    binding.rvCategoryType.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryType.root.isVisible = false
                    binding.layoutStateCategoryType.tvError.isVisible = false
                    binding.layoutStateCategoryType.pbLoading.isVisible = false
                    binding.layoutStateCategoryType.clErrorState.isVisible = true
                    binding.layoutStateCategoryType.tvErrorState.isVisible = true
                    binding.layoutStateCategoryType.ivErrorState.isVisible = false
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
                    binding.layoutCommonState.root.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.clErrorState.isVisible = false
                    binding.layoutStateCourse.tvErrorState.isVisible = false
                    binding.layoutStateCourse.ivErrorState.isVisible = false
                },
                doOnSuccess = { result ->
                    binding.rvCourse.isVisible = true
                    binding.rvCourse.adapter = typeCourseAdapter
                    binding.shimmerCourseLinear.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutCommonState.root.isVisible = false
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.clErrorState.isVisible = false
                    binding.layoutStateCourse.tvErrorState.isVisible = false
                    binding.layoutStateCourse.ivErrorState.isVisible = false
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
                    binding.layoutStateCourse.clErrorState.isVisible = false
                    binding.layoutStateCourse.tvErrorState.isVisible = false
                    binding.layoutStateCourse.ivErrorState.isVisible = false

                    if (it.exception is ApiException) {
                        binding.layoutCommonState.root.isVisible = true
                        binding.layoutStateCourse.root.isVisible = false
                        if (it.exception.getParsedErrorCourse()?.success == false) {
                            binding.layoutCommonState.tvError.text =
                                it.exception.getParsedErrorCourse()?.message
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clErrorState.isGone = false
                                binding.layoutCommonState.ivErrorState.isGone = false
                                binding.layoutCommonState.tvErrorState.isGone = false
                                binding.layoutCommonState.tvErrorState.text =
                                    getString(R.string.text_sorry_there_s_an_error_on_the_server)
                                binding.layoutCommonState.ivErrorState.setImageResource(R.drawable.img_server_error)
                            }
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(requireContext())) {
                            binding.layoutCommonState.root.isVisible = true
                            binding.layoutStateCourse.root.isVisible = false
                            binding.layoutCommonState.clErrorState.isGone = false
                            binding.layoutCommonState.ivErrorState.isGone = false
                            binding.layoutCommonState.tvErrorState.isGone = false
                            binding.layoutCommonState.tvErrorState.text = getString(R.string.text_no_internet_connection)
                            binding.layoutCommonState.ivErrorState.setImageResource(R.drawable.img_no_connection)
                        }
                    }
                },
                doOnEmpty = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseLinear.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutCommonState.root.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.clErrorState.isVisible = true
                    binding.layoutStateCourse.tvErrorState.isVisible = true
                    binding.layoutStateCourse.tvErrorState.text = getString(R.string.text_class_not_found)
                    binding.layoutStateCourse.ivErrorState.isVisible = true
                }
            )
        }

        viewModel.isUserLogin.observe(viewLifecycleOwner) { isLogin ->
            if (!isLogin) {
                showDialog()
            }
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

    private var selectedType: String? = null
    private var selectedCategories: List<CategoryClass>? = null
    override fun onFilterApplied(type: String?, category: List<CategoryClass>?) {
        selectedType = type
        selectedCategories = category
        val categoryId = category?.map {
            it.name
        }
        viewModel.getCourse(categoryId, selectedType)
    }
}
