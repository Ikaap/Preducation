package com.kelompoksatuandsatu.preducation.presentation.feature.classProgress

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.databinding.FragmentProgressClassBinding
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CategoryCourseListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CourseCardListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity

class ProgressClassFragment : Fragment() {

    private lateinit var binding: FragmentProgressClassBinding

    private val categoryCourseAdapter: CategoryCourseListAdapter by lazy {
        CategoryCourseListAdapter {
            showSuccessDialog()
        }
    }

    private val progressCourseAdapter: CourseCardListAdapter by lazy {
        CourseCardListAdapter(AdapterLayoutMenu.CLASS) {
            showSuccessDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProgressClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        showCategoryCourse()
        showCourse()
        showCategoryProgress()
        binding.rvProgressCourse.setOnClickListener {
            showSuccessDialog()
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

    private fun showCategoryProgress() {
//        val categoryPopularAdapter = CategoryCourseRoundedListAdapter()
//        binding.rvCategoryProgress.adapter = categoryPopularAdapter
//        binding.rvCategoryProgress.layoutManager = LinearLayoutManager(
//            requireContext(),
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
//        val dummyCategoryPopularDataSource: DummyCategoryPopularDataSource =
//            DummyCategoryPopularDataSourceImpl()
//        val categoryPopularList: List<CategoryPopular> =
//            dummyCategoryPopularDataSource.getCategoryProgress()
//        categoryPopularAdapter.setData(categoryPopularList)
    }

    private fun showCourse() {
//        binding.rvProgressCourse.adapter = progressCourseAdapter
//        binding.rvProgressCourse.layoutManager = LinearLayoutManager(
//            requireContext(),
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
//        val dummyPopularDataSource: DummyPopularCourseDataSource =
//            DummyPopularCourseDataSourceImpl()
//        val popularCourseList: List<Course> =
//            dummyPopularDataSource.getPopularCourse()
//        progressCourseAdapter.setData(popularCourseList)
    }

    private fun showCategoryCourse() {
//        binding.rvCategoryCourse.adapter = categoryCourseAdapter
//        binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireContext(), 4)
//        val dummyCategoryCourseDataSource: DummyCategoryCourseDataSource =
//            DummyCategoryCourseDataSourceImpl()
//        val categoryCourseList: List<CategoryCourse> =
//            dummyCategoryCourseDataSource.getCategoryCourse()
//        categoryCourseAdapter.setData(categoryCourseList)
    }
}
