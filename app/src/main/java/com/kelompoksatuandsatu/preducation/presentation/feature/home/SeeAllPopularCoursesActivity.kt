package com.kelompoksatuandsatu.preducation.presentation.feature.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kelompoksatuandsatu.preducation.databinding.ActivitySeeAllPopularCoursesBinding
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.CourseLinearListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeeAllPopularCoursesActivity : AppCompatActivity() {

    private val binding: ActivitySeeAllPopularCoursesBinding by lazy {
        ActivitySeeAllPopularCoursesBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by viewModel()

    private val courseAdapter: CourseLinearListAdapter by lazy {
        CourseLinearListAdapter(AdapterLayoutMenu.SEEALL) {
            viewModel.isUserLogin.observe(this) { isLogin ->
                if (!isLogin) {
                    showDialog()
                } else {
                    navigateToDetail(it)
                }
            }
        }
    }

    private fun navigateToDetail(course: CourseViewParam) {
        val intent = Intent(this, DetailClassActivity::class.java).apply {
            putExtra("EXTRA_COURSE_ID", course.id)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val categoryName = intent.getStringExtra("CATEGORY_NAME")
        getData(categoryName)
        observeData()

        viewModel.checkLogin()
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getData(categoryName: String?) {
        viewModel.getCourse(categoryName)
    }

    private fun observeData() {
        viewModel.coursePopular.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnSuccess = {
                    binding.rvCourse.isVisible = true
                    binding.rvCourse.adapter = courseAdapter
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.tvError.isVisible = false
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.tvDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.ivDataEmpty.isVisible = false
                    it.payload?.let {
                        courseAdapter.setData(it)
                    }
                },
                doOnLoading = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = true
                    binding.layoutStateCoursePopular.root.isVisible = false
                    binding.layoutStateCoursePopular.tvError.isVisible = false
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.tvDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.ivDataEmpty.isVisible = false
                },
                doOnError = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isVisible = true
                    binding.layoutStateCoursePopular.tvError.isVisible = true
                    binding.layoutStateCoursePopular.tvError.text = it.exception?.message
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.tvDataEmpty.isVisible = false
                    binding.layoutStateCoursePopular.ivDataEmpty.isVisible = false
                },
                doOnEmpty = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isVisible = true
                    binding.layoutStateCoursePopular.tvError.isVisible = false
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clDataEmpty.isVisible = true
                    binding.layoutStateCoursePopular.tvDataEmpty.isVisible = true
                    binding.layoutStateCoursePopular.ivDataEmpty.isVisible = true
                }
            )
        }
    }

    private fun showDialog() {
        val binding: DialogNonLoginBinding = DialogNonLoginBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this, 0).create()

        dialog.apply {
            setView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()

        binding.clSignUp.setOnClickListener {
            val intent = Intent(this@SeeAllPopularCoursesActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, SeeAllPopularCoursesActivity::class.java)
            context.startActivity(intent)
        }
    }
}
