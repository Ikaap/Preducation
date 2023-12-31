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
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivitySeeAllPopularCoursesBinding
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CourseLinearListAdapter
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
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
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getData(categoryName: String?) {
        viewModel.getCourse(categoryName)
        viewModel.checkLogin()
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
                    binding.layoutStateCoursePopular.clErrorState.isVisible = false
                    binding.layoutStateCoursePopular.tvErrorState.isVisible = false
                    binding.layoutStateCoursePopular.ivErrorState.isVisible = false
                    it.payload?.let {
                        courseAdapter.setData(it)
                    }
                },
                doOnLoading = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = true
                    binding.layoutStateCoursePopular.root.isVisible = false
                    binding.layoutCommonState.root.isVisible = true
                    binding.layoutStateCoursePopular.tvError.isVisible = false
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clErrorState.isVisible = false
                    binding.layoutStateCoursePopular.tvErrorState.isVisible = false
                    binding.layoutStateCoursePopular.ivErrorState.isVisible = false
                },
                doOnError = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isVisible = true
                    binding.layoutStateCoursePopular.tvError.isVisible = true
                    binding.layoutStateCoursePopular.tvError.text = it.exception?.message
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clErrorState.isVisible = false
                    binding.layoutStateCoursePopular.tvErrorState.isVisible = false
                    binding.layoutStateCoursePopular.ivErrorState.isVisible = false

                    if (it.exception is ApiException) {
                        binding.layoutCommonState.root.isVisible = true
                        if (it.exception.getParsedErrorCourse()?.success == false) {
                            binding.layoutCommonState.tvError.text =
                                it.exception.getParsedErrorCourse()?.message
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clErrorState.isGone = false
                                binding.layoutCommonState.ivErrorState.isGone = false
                                binding.layoutCommonState.tvErrorState.isGone = false
                                binding.layoutCommonState.tvErrorState.text = "Sorry, there's an error on the server"
                                binding.layoutCommonState.ivErrorState.setImageResource(R.drawable.img_server_error)
                            }
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(this)) {
                            binding.layoutCommonState.root.isVisible = true
                            binding.layoutCommonState.clErrorState.isGone = false
                            binding.layoutCommonState.ivErrorState.isGone = false
                            binding.layoutCommonState.tvErrorState.isGone = false
                            binding.layoutCommonState.tvErrorState.text = "Oops!\nYou're not connection"
                            binding.layoutCommonState.ivErrorState.setImageResource(R.drawable.img_no_connection)
                        }
                    }
                },
                doOnEmpty = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseCard.isVisible = false
                    binding.layoutStateCoursePopular.root.isVisible = true
                    binding.layoutStateCoursePopular.tvError.isVisible = false
                    binding.layoutStateCoursePopular.pbLoading.isVisible = false
                    binding.layoutStateCoursePopular.clErrorState.isVisible = true
                    binding.layoutStateCoursePopular.tvErrorState.isVisible = true
                    binding.layoutStateCoursePopular.tvErrorState.text = "Class not found !"
                    binding.layoutStateCoursePopular.ivErrorState.isVisible = true
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
