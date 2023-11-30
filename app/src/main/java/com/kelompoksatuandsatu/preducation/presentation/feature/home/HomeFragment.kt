package com.kelompoksatuandsatu.preducation.presentation.feature.home

import android.app.AlertDialog
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
import com.kelompoksatuandsatu.preducation.model.PopularCourse
import com.kelompoksatuandsatu.preducation.presentation.feature.home.adapter.CategoryCourseListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.home.adapter.CategoryPopularListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.home.adapter.PopularCourseListAdapter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val categoryCourseAdapter: CategoryCourseListAdapter by lazy {
        CategoryCourseListAdapter {
            showSuccessDialog()
        }
    }

    private val popularCourseAdapter: PopularCourseListAdapter by lazy {
        PopularCourseListAdapter {
            showSuccessDialog()
        }
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
    }

    private fun showSuccessDialog() {
        val binding: DialogNonLoginBinding = DialogNonLoginBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext(), 0).create()

        dialog.apply {
            setView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()
    }

    private fun showCategoryPopular() {
        val categoryPopularAdapter = CategoryPopularListAdapter()
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
        val popularCourseList: List<PopularCourse> =
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
