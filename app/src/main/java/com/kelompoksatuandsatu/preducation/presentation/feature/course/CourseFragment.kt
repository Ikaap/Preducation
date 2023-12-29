package com.kelompoksatuandsatu.preducation.presentation.feature.course

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
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseFragment : Fragment(), FilterFragment.OnFilterListener {

    private lateinit var binding: FragmentCourseBinding

    private val filterFragment: FilterFragment by lazy {
        FilterFragment()
    }

    private val viewModel: CourseViewModel by viewModel()

    private val typeCourseAdapter: CourseLinearListAdapter by lazy {
        CourseLinearListAdapter(AdapterLayoutMenu.COURSE) {
            viewModel.isUserLogin.observe(viewLifecycleOwner) { isLogin ->
                if (!isLogin) {
                    // showDialog()
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

    private val searchView: SearchView by lazy {
        binding.clSearchBar.findViewById(R.id.sv_search)
    }

    private val searchQueryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
//                typeCourseAdapter.filter(it)
//                observeIsFilterEmpty()
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

    private fun navigateToSearch(course: CourseViewParam) {
        SearchActivity.startActivity(requireContext(), course)
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
            filterFragment.show(childFragmentManager, "Filter")
        }

        binding.clSearchBar.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val FILTER_REQUEST_CODE = 123
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

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorCategoriesType()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clServerError.isGone = false
                                binding.layoutCommonState.ivServerError.isGone = false
                                StyleableToast.makeText(
                                    requireContext(),
                                    "SERVER ERROR",
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorCategoriesType()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorCategoriesType()?.message
                                StyleableToast.makeText(
                                    requireContext(),
                                    it.exception.getParsedErrorCategoriesType()?.message,
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

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorCategoriesType()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clServerError.isGone = false
                                binding.layoutCommonState.ivServerError.isGone = false
                                StyleableToast.makeText(
                                    requireContext(),
                                    "SERVER ERROR",
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorCategoriesType()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorCategoriesType()?.message
                                StyleableToast.makeText(
                                    requireContext(),
                                    it.exception.getParsedErrorCategoriesType()?.message,
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
                    binding.layoutStateCourse.tvError.isVisible = true

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorCourse()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clServerError.isGone = false
                                binding.layoutCommonState.ivServerError.isGone = false
                                StyleableToast.makeText(
                                    requireContext(),
                                    "SERVER ERROR",
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorCourse()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorCourse()?.message
                                StyleableToast.makeText(
                                    requireContext(),
                                    it.exception.getParsedErrorCourse()?.message,
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
    }

    private fun showDialogNotification() {
        val binding: LayoutDialogAccessFeatureBinding = LayoutDialogAccessFeatureBinding.inflate(layoutInflater)
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
