package com.kelompoksatuandsatu.preducation.presentation.feature.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyCategoryPopularDataSource
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyCategoryPopularDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyPopularCourseDataSource
import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyPopularCourseDataSourceImpl
import com.kelompoksatuandsatu.preducation.databinding.ActivitySeeAllPopularCoursesBinding
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.model.CategoryPopular
import com.kelompoksatuandsatu.preducation.model.Course
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CategoryCourseRoundedListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CourseLinearListAdapter

class SeeAllPopularCoursesActivity : AppCompatActivity() {

    private val binding: ActivitySeeAllPopularCoursesBinding by lazy {
        ActivitySeeAllPopularCoursesBinding.inflate(layoutInflater)
    }

    private val courseAdapter: CourseLinearListAdapter by lazy {
        CourseLinearListAdapter(AdapterLayoutMenu.SEEALL) {
            showSuccessDialog()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showCourse()
        showCategoryPopular()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showCourse() {
        binding.rvCourse.adapter = courseAdapter
        binding.rvCourse.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        val dummyPopularDataSource: DummyPopularCourseDataSource =
            DummyPopularCourseDataSourceImpl()
        val popularCourseList: List<Course> =
            dummyPopularDataSource.getPopularCourse()
        courseAdapter.setData(popularCourseList)
    }

    private fun showCategoryPopular() {
        val categoryPopularAdapter = CategoryCourseRoundedListAdapter {}
        binding.rvCategoryPopular.adapter = categoryPopularAdapter
        binding.rvCategoryPopular.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val dummyCategoryPopularDataSource: DummyCategoryPopularDataSource =
            DummyCategoryPopularDataSourceImpl()
        val categoryPopularList: List<CategoryPopular> =
            dummyCategoryPopularDataSource.getCategoryPopular()
        categoryPopularAdapter.setData(categoryPopularList)
    }

    private fun showSuccessDialog() {
        val binding: DialogNonLoginBinding = DialogNonLoginBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this, 0).create()

        dialog.apply {
            setView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, SeeAllPopularCoursesActivity::class.java)
            context.startActivity(intent)
        }
    }
}
