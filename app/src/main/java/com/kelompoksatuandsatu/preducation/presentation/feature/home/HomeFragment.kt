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
import com.kelompoksatuandsatu.preducation.presentation.feature.search.SearchActivity
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

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
            viewModel.getCourse(it.name, null)
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
//        searchView.setOnQueryTextListener(searchQueryListener)
    }

    private fun setOnClickListener() {
        binding.tvNavToSeeAllTitleCategory.setOnClickListener {
            SeeAllPopularCoursesActivity.startActivity(requireContext())
        }
        binding.ivToSeeAll.setOnClickListener {
            SeeAllPopularCoursesActivity.startActivity(requireContext())
        }
        binding.clSearchBar.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getData() {
        viewModel.getCategoriesClass()
        viewModel.getCategoriesClassPopular()
        viewModel.getCourse()
        viewModel.checkLogin()
        viewModel.getUserById()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserById()
        setDataProfile()
    }

    private fun setDataProfile() {
        viewModel.getProfile.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.root.isVisible = true
                    it.payload?.let {
                        val userName = it.name.orEmpty()
                        val welcomeMessage = getString(R.string.text_header_name, userName)
                        binding.tvHeaderName.text = welcomeMessage
                    }
                },
                doOnEmpty = {
                    val welcomeMessageNonLogin = getString(R.string.text_header_name_non_login)
                    binding.tvHeaderName.text = welcomeMessageNonLogin
                }
            )
        }
    }

    private fun observeData() {
        viewModel.categoriesClass.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvCategoryCourse.isVisible = true
                    binding.rvCategoryCourse.adapter = categoryCourseAdapter
                    binding.shimmerCategoryCircle.isVisible = false
                    binding.layoutStateCoursePopular.tvError.isVisible = false
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.tvDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.ivDataEmpty.isVisible = false
                    it.payload?.let { data ->
                        categoryCourseAdapter.setData(data)
                    }
                },
                doOnLoading = {
                    binding.rvCategoryCourse.isVisible = false
                    binding.shimmerCategoryCircle.isVisible = true
                    binding.layoutStateCategoryCircle.root.isVisible = false
                    binding.layoutStateCategoryCircle.tvError.isVisible = false
                    binding.layoutStateCategoryCircle.pbLoading.isVisible = false
                    binding.layoutStateCategoryCircle.clDataEmpty.isVisible = false
                    binding.layoutStateCategoryCircle.tvDataEmpty.isVisible = false
                    binding.layoutStateCategoryCircle.ivDataEmpty.isVisible = false
                },
                doOnError = {
                    binding.rvCategoryCourse.isVisible = false
                    binding.shimmerCategoryCircle.isVisible = false
                    binding.layoutStateCategoryCircle.root.isVisible = true
                    binding.layoutStateCategoryCircle.tvError.isVisible = true
                    binding.layoutStateCategoryCircle.tvError.text = it.exception?.message
                    binding.layoutStateCategoryCircle.pbLoading.isVisible = false
                    binding.layoutStateCategoryCircle.clDataEmpty.isVisible = false
                    binding.layoutStateCategoryCircle.tvDataEmpty.isVisible = false
                    binding.layoutStateCategoryCircle.ivDataEmpty.isVisible = false

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorCategories()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clServerError.isGone = false
                                binding.layoutCommonState.ivServerError.isGone = false
                                StyleableToast.makeText(
                                    requireContext(),
                                    "SERVER ERROR",
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorCategories()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorCategories()?.message
                                StyleableToast.makeText(
                                    requireContext(),
                                    it.exception.getParsedErrorCategories()?.message,
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
                },
                doOnEmpty = {
                    binding.rvCategoryCourse.isVisible = false
                    binding.shimmerCategoryCircle.isVisible = false
                    binding.layoutStateCategoryCircle.root.isVisible = true
                    binding.layoutStateCategoryCircle.tvError.isVisible = false
                    binding.layoutStateCategoryCircle.pbLoading.isVisible = false
                    binding.layoutStateCategoryCircle.clDataEmpty.isVisible = true
                    binding.layoutStateCategoryCircle.tvDataEmpty.isVisible = true
                    binding.layoutStateCategoryCircle.ivDataEmpty.isVisible = false
                }
            )
        }

        viewModel.categoriesClassPopular.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvCategoryPopular.isVisible = true
                    binding.rvCategoryPopular.adapter = categoryCoursePopularAdapter
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryRounded.root.isVisible = false
                    binding.layoutStateCategoryRounded.tvError.isVisible = false
                    binding.layoutStateCategoryRounded.pbLoading.isVisible = false
                    binding.layoutStateCategoryRounded.clDataEmpty.isVisible = false
                    binding.layoutStateCategoryRounded.tvDataEmpty.isVisible = false
                    binding.layoutStateCategoryRounded.ivDataEmpty.isVisible = false
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
                    binding.layoutStateCategoryRounded.root.isVisible = false
                    binding.layoutStateCategoryRounded.tvError.isVisible = false
                    binding.layoutStateCategoryRounded.pbLoading.isVisible = false
                    binding.layoutStateCategoryRounded.clDataEmpty.isVisible = false
                    binding.layoutStateCategoryRounded.tvDataEmpty.isVisible = false
                    binding.layoutStateCategoryRounded.ivDataEmpty.isVisible = false
                },
                doOnError = {
                    binding.rvCategoryPopular.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryRounded.root.isVisible = true
                    binding.layoutStateCategoryRounded.tvError.isVisible = true
                    binding.layoutStateCategoryRounded.tvError.text = it.exception?.message
                    binding.layoutStateCategoryRounded.pbLoading.isVisible = false
                    binding.layoutStateCategoryRounded.clDataEmpty.isVisible = false
                    binding.layoutStateCategoryRounded.tvDataEmpty.isVisible = false
                    binding.layoutStateCategoryRounded.ivDataEmpty.isVisible = false

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorCategories()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clServerError.isGone = false
                                binding.layoutCommonState.ivServerError.isGone = false
                                StyleableToast.makeText(
                                    requireContext(),
                                    "SERVER ERROR",
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorCategories()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorCategories()?.message
                                StyleableToast.makeText(
                                    requireContext(),
                                    it.exception.getParsedErrorCategories()?.message,
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
                },
                doOnEmpty = {
                    binding.rvCategoryPopular.isVisible = false
                    binding.shimmerCategoryRounded.isVisible = false
                    binding.layoutStateCategoryRounded.root.isVisible = false
                    binding.layoutStateCategoryRounded.tvError.isVisible = false
                    binding.layoutStateCategoryRounded.pbLoading.isVisible = false
                    binding.layoutStateCategoryRounded.clDataEmpty.isVisible = true
                    binding.layoutStateCategoryRounded.tvDataEmpty.isVisible = true
                    binding.layoutStateCategoryRounded.ivDataEmpty.isVisible = false
                }
            )
        }

        viewModel.coursePopular.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvPopularCourse.isVisible = true
                    binding.rvPopularCourse.adapter = popularCourseAdapter
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.tvError.isVisible = false
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.tvDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.ivDataEmpty.isVisible = false
                    it.payload?.let {
                        popularCourseAdapter.setData(it)
                    }
                },
                doOnLoading = {
                    binding.rvPopularCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = true
                    binding.layoutStateCoursePopular.root.isVisible = false
                    binding.layoutStateCoursePopular.tvError.isVisible = false
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.tvDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.ivDataEmpty.isVisible = false
                },
                doOnError = {
                    binding.rvPopularCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isVisible = true
                    binding.layoutStateCoursePopular.tvError.isVisible = true
                    binding.layoutStateCoursePopular.tvError.text = it.exception?.message
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.tvDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.ivDataEmpty.isVisible = false

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
                },
                doOnEmpty = {
                    binding.rvPopularCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isVisible = true
                    binding.layoutStateCoursePopular.tvError.isVisible = false
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clDataEmpty.isVisible = true
                    binding.layoutStateCoursePopular.tvDataEmpty.isVisible = true
                    binding.layoutStateCoursePopular.ivDataEmpty.isVisible = false
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
