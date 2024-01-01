package com.kelompoksatuandsatu.preducation.presentation.feature.search

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivitySearchBinding
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.course.CourseLinearListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.register.RegisterActivity
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private fun search(query: String) {
        viewModel.getCourse(null, null, query)
    }

    private val searchView: SearchView by lazy {
        binding.clSearchBar.findViewById(R.id.sv_search)
    }

    private val viewModel: SearchViewModel by viewModel()

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

        fetchData()
        observeData()
        setOnClickListener()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    search(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun setOnClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
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
            val intent = Intent(this@SearchActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchData() {
        viewModel.checkLogin()
    }

    private fun observeData() {
        binding.rvCourse.adapter = courseAdapter
        binding.rvCourse.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        viewModel.course.observe(this) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnSuccess = {
                    binding.rvCourse.isVisible = true
                    binding.rvCourse.adapter = courseAdapter
                    binding.shimmerCourseLinear.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.clErrorState.isVisible = false
                    binding.layoutStateCourse.tvErrorState.isVisible = false
                    binding.layoutStateCourse.ivErrorState.isVisible = false
                    it.payload?.let {
                        courseAdapter.setData(it)
                    }
                },
                doOnLoading = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseLinear.isVisible = true
                    binding.layoutStateCourse.root.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.clErrorState.isVisible = false
                    binding.layoutStateCourse.tvErrorState.isVisible = false
                    binding.layoutStateCourse.ivErrorState.isVisible = false
                },
                doOnError = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseLinear.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.tvError.isVisible = true
                    binding.layoutStateCourse.tvError.text = it.exception?.message
                    binding.layoutStateCourse.pbLoading.isVisible = false

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorCourse()?.success == false) {
                            binding.layoutStateCourse.tvError.text =
                                it.exception.getParsedErrorCourse()?.message
                            if (it.exception.httpCode == 500) {
                                binding.layoutStateCourse.clErrorState.isGone = false
                                binding.layoutStateCourse.ivErrorState.isGone = false
                                binding.layoutStateCourse.tvErrorState.isGone = false
                                binding.layoutStateCourse.tvErrorState.text =
                                    "Sorry, there's an error on the server"
                                binding.layoutStateCourse.ivErrorState.setImageResource(R.drawable.img_server_error)
                            }
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(this)) {
                            binding.layoutStateCourse.clErrorState.isGone = false
                            binding.layoutStateCourse.ivErrorState.isGone = false
                            binding.layoutStateCourse.tvErrorState.isGone = false
                            binding.layoutStateCourse.tvErrorState.text =
                                "Oops!\nYou're not connection"
                            binding.layoutStateCourse.ivErrorState.setImageResource(R.drawable.img_no_connection)
                        }
                    }
                },
                doOnEmpty = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourseLinear.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.clErrorState.isVisible = true
                    binding.layoutStateCourse.tvErrorState.isVisible = true
                    binding.layoutStateCourse.tvErrorState.text = "Class not found"
                    binding.layoutStateCourse.ivErrorState.isVisible = true
                }
            )
        }
    }

    companion object {
        const val EXTRA_COURSE_ID = "EXTRA_COURSE_ID"
        fun startActivity(context: Context, course: CourseViewParam) {
            val id = course.id
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra(EXTRA_COURSE_ID, id)
            context.startActivity(intent)
        }
    }
}
