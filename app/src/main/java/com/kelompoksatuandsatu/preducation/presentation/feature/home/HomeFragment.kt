package com.kelompoksatuandsatu.preducation.presentation.feature.home

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyCategoryCourseDataSource
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyCategoryCourseDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyCategoryPopularDataSource
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyCategoryPopularDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyPopularCourseDataSource
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyPopularCourseDataSourceImpl
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.databinding.FragmentHomeBinding
import com.kelompoksatuandsatu.preducation.model.CategoryCourse
import com.kelompoksatuandsatu.preducation.model.CategoryPopular
import com.kelompoksatuandsatu.preducation.model.Course
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CategoryCourseListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CategoryCourseRoundedListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CourseCardListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val categoryCourseAdapter: CategoryCourseListAdapter by lazy {
        CategoryCourseListAdapter {
            showSuccessDialog()
        }
    }

    private val popularCourseAdapter: CourseCardListAdapter by lazy {
        CourseCardListAdapter(AdapterLayoutMenu.HOME) {
            showSuccessDialog()
            navigateToDetail(it)
        }
    }

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

        showCategoryCourse()
        showPopularCourse()
        showCategoryPopular()

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

    private fun showCategoryPopular() {
        val categoryPopularAdapter = CategoryCourseRoundedListAdapter()
        binding.rvCategoryPopular.adapter = categoryPopularAdapter
        binding.rvCategoryPopular.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val dummyCategoryPopularDataSource: DummyCategoryPopularDataSource =
            DummyCategoryPopularDataSourceImpl()
        val categoryPopularList: List<CategoryPopular> =
            dummyCategoryPopularDataSource.getCategoryPopular()
        categoryPopularAdapter.setData(categoryPopularList)
    }

    private fun showPopularCourse() {
        binding.rvPopularCourse.adapter = popularCourseAdapter
        binding.rvPopularCourse.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val dummyPopularDataSource: DummyPopularCourseDataSource =
            DummyPopularCourseDataSourceImpl()
        val popularCourseList: List<Course> =
            dummyPopularDataSource.getPopularCourse()
        popularCourseAdapter.setData(popularCourseList)
    }

    private fun showCategoryCourse() {
        binding.rvCategoryCourse.adapter = categoryCourseAdapter
        binding.rvCategoryCourse.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val dummyCategoryCourseDataSource: DummyCategoryCourseDataSource =
            DummyCategoryCourseDataSourceImpl()
        val categoryCourseList: List<CategoryCourse> =
            dummyCategoryCourseDataSource.getCategoryCourse()
        categoryCourseAdapter.setData(categoryCourseList)
    }
}
