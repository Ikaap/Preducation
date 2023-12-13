package com.kelompoksatuandsatu.preducation.presentation.feature.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.kelompoksatuandsatu.preducation.databinding.ActivitySeeAllPopularCoursesBinding
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CourseLinearListAdapter
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
            showSuccessDialog()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val categoryName = intent.getStringExtra("CATEGORY_NAME")

        getData(categoryName)
        observeData()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getData(categoryName: String?) {
        viewModel.getCourse(categoryName)
    }

    private fun observeData() {
        viewModel.coursePopular.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvCourse.isVisible = true
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isGone = true
                    binding.layoutStateCoursePopular.ivDataEmpty.isGone = true
                    binding.layoutStateCoursePopular.tvError.isGone = true
                    binding.layoutStateCoursePopular.tvDataEmpty.isGone = true
                    binding.rvCourse.apply {
                        isVisible = true
                        adapter = courseAdapter
                    }
                    it.payload?.let { data ->
                        courseAdapter.setData(data)
                    }
                },
                doOnLoading = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = true
                    binding.layoutStateCoursePopular.root.isGone = true
                    binding.layoutStateCoursePopular.ivDataEmpty.isGone = true
                    binding.layoutStateCoursePopular.tvError.isGone = true
                    binding.layoutStateCoursePopular.tvDataEmpty.isGone = true
                },
                doOnEmpty = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isGone = false
                    binding.layoutStateCoursePopular.ivDataEmpty.isGone = true
                    binding.layoutStateCoursePopular.tvError.isGone = false
                    binding.layoutStateCoursePopular.tvError.text = "Data Empty"
                    binding.layoutStateCoursePopular.tvDataEmpty.isGone = true
                },
                doOnError = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isGone = false
                    binding.layoutStateCoursePopular.ivDataEmpty.isGone = true
                    binding.layoutStateCoursePopular.tvError.isGone = false
                    binding.layoutStateCoursePopular.tvError.text = it.exception?.message
                    binding.layoutStateCoursePopular.tvDataEmpty.isGone = true
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
