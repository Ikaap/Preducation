package com.kelompoksatuandsatu.preducation.presentation.feature.course

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
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyCategoryPopularDataSource
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyCategoryPopularDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyPopularCourseDataSource
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyPopularCourseDataSourceImpl
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.databinding.FragmentCourseBinding
import com.kelompoksatuandsatu.preducation.model.CategoryPopular
import com.kelompoksatuandsatu.preducation.model.Course
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CategoryCourseRoundedListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CourseLinearListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.filter.FilterActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding

    private val typeCourseAdapter: CourseLinearListAdapter by lazy {
        CourseLinearListAdapter(AdapterLayoutMenu.COURSE) {
            showSuccessDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)

        showCourseType()
        showCategoryType()
        binding.rvCourse.setOnClickListener {
            showSuccessDialog()
        }

        binding.tvFilter.setOnClickListener {
            val intent = Intent(requireContext(), FilterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showCategoryType() {
        val categoryPopularAdapter = CategoryCourseRoundedListAdapter {}
        binding.rvCategoryType.adapter = categoryPopularAdapter
        binding.rvCategoryType.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val dummyCategoryPopularDataSource: DummyCategoryPopularDataSource =
            DummyCategoryPopularDataSourceImpl()
        val categoryPopularList: List<CategoryPopular> =
            dummyCategoryPopularDataSource.getCategoryType()
        categoryPopularAdapter.setData(categoryPopularList)
    }

    private fun showCourseType() {
        binding.rvCourse.adapter = typeCourseAdapter
        binding.rvCourse.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        val dummyPopularDataSource: DummyPopularCourseDataSource =
            DummyPopularCourseDataSourceImpl()
        val popularCourseList: List<Course> =
            dummyPopularDataSource.getPopularCourse()
        typeCourseAdapter.setData(popularCourseList)
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
