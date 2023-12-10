package com.kelompoksatuandsatu.preducation.presentation.feature.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivitySeeAllPopularCoursesBinding
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CategoryCourseRoundedListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CourseLinearListAdapter
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeeAllPopularCoursesActivity : AppCompatActivity() {

    private val binding: ActivitySeeAllPopularCoursesBinding by lazy {
        ActivitySeeAllPopularCoursesBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by viewModel()

    private val categoryCoursePopularAdapter: CategoryCourseRoundedListAdapter by lazy {
        CategoryCourseRoundedListAdapter(viewModel) {
            viewModel.getCourse(it.name)
        }
    }

    private val courseAdapter: CourseLinearListAdapter by lazy {
        CourseLinearListAdapter(AdapterLayoutMenu.SEEALL) {
            showSuccessDialog()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getData()
        observeData()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getData() {
        viewModel.getCategoriesClassPopular()
        viewModel.getCourse()
    }

    private fun observeData() {
        viewModel.categoriesClassPopular.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvCategoryPopular.apply {
                        binding.rvCategoryPopular.layoutManager = LinearLayoutManager(
                            this@SeeAllPopularCoursesActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = categoryCoursePopularAdapter
                    }
                    it.payload?.let { data ->
                        categoryCoursePopularAdapter.setData(data)
                    }
                }
            )
        }

        viewModel.course.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutStateSeeallPopularCourse.root.isVisible = false
                    binding.layoutStateSeeallPopularCourse.pbLoading.isVisible = false
                    binding.layoutStateSeeallPopularCourse.tvError.isVisible = false
                    binding.rvCourse.apply {
                        isVisible = true
                        adapter = courseAdapter
                    }
                    it.payload?.let { data ->
                        courseAdapter.setData(data)
                    }
                },
                doOnLoading = {
                    binding.layoutStateSeeallPopularCourse.root.isVisible = true
                    binding.layoutStateSeeallPopularCourse.pbLoading.isVisible = true
                    binding.layoutStateSeeallPopularCourse.tvError.isVisible = false
                    binding.rvCourse.isVisible = false
                },
                doOnError = {
                    binding.layoutStateSeeallPopularCourse.root.isVisible = true
                    binding.layoutStateSeeallPopularCourse.pbLoading.isVisible = false
                    binding.layoutStateSeeallPopularCourse.tvError.isVisible = true
                    binding.layoutStateSeeallPopularCourse.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCourse.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutStateSeeallPopularCourse.root.isVisible = true
                    binding.layoutStateSeeallPopularCourse.pbLoading.isVisible = false
                    binding.layoutStateSeeallPopularCourse.tvError.isVisible = true
                    binding.layoutStateSeeallPopularCourse.tvError.text =
                        resources.getString(R.string.popular_course_not_found)
                    binding.rvCourse.isVisible = false
                }
            )
        }
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
