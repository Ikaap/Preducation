package com.kelompoksatuandsatu.preducation.presentation.feature.historypayment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.CourseItem
import com.kelompoksatuandsatu.preducation.databinding.ActivityTransactionBinding
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.history.HistoryPaymentListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryPaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionBinding

    private val viewModel: HistoryPaymentViewModel by viewModel()

    private val historyAdapter: HistoryPaymentListAdapter by lazy {
        HistoryPaymentListAdapter {
            navigateToDetail(it)
        }
    }

    private fun navigateToDetail(it: CourseItem) {
        val intent = Intent(this, DetailClassActivity::class.java)
        intent.putExtra(EXTRA_DETAIL_COURSE_ID, it.courseId?.id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        getData()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.rvHistory.apply {
            layoutManager =
                LinearLayoutManager(this@HistoryPaymentActivity, RecyclerView.VERTICAL, false)
            adapter = historyAdapter
        }
    }

    private fun observeData() {
        viewModel.payment.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutStateHistory.root.isGone = true
                    binding.layoutStateHistory.pbLoading.isGone = true
                    binding.layoutStateHistory.tvError.isGone = true
                    binding.layoutStateHistory.clErrorState.isGone = true
                    binding.layoutStateHistory.tvErrorState.isGone = true
                    binding.layoutStateHistory.ivErrorState.isGone = true

                    binding.rvHistory.apply {
                        isVisible = true
                        adapter = historyAdapter
                        layoutManager = LinearLayoutManager(
                            this@HistoryPaymentActivity,
                            RecyclerView.VERTICAL,
                            false
                        )
                    }
                    it.payload?.let {
                        historyAdapter.setData(it)
                    }
                },
                doOnLoading = {
                    binding.rvHistory.isVisible = false
                    binding.layoutStateHistory.root.isGone = false
                    binding.layoutStateHistory.pbLoading.isGone = false
                    binding.layoutStateHistory.tvError.isGone = true
                    binding.layoutStateHistory.clErrorState.isGone = true
                    binding.layoutStateHistory.tvErrorState.isGone = true
                    binding.layoutStateHistory.ivErrorState.isGone = true
                },
                doOnEmpty = {
                    binding.rvHistory.isVisible = false
                    binding.layoutStateHistory.root.isGone = false
                    binding.layoutStateHistory.pbLoading.isGone = true
                    binding.layoutStateHistory.tvError.isGone = true
                    binding.layoutStateHistory.clErrorState.isGone = false
                    binding.layoutStateHistory.tvErrorState.isGone = false
                    binding.layoutStateHistory.tvErrorState.text = "Data empty"
                    binding.layoutStateHistory.ivErrorState.isGone = false
                },
                doOnError = {
                    binding.rvHistory.isVisible = false
                    binding.layoutStateHistory.root.isGone = false
                    binding.layoutStateHistory.pbLoading.isGone = true
                    binding.layoutStateHistory.tvError.isGone = false
                    binding.layoutStateHistory.tvError.text = it.exception?.message.toString()

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorHistoryPayment()?.success == false) {
                            binding.layoutStateHistory.tvError.text =
                                it.exception.getParsedErrorHistoryPayment()?.message
                            if (it.exception.httpCode == 500) {
                                binding.layoutStateHistory.clErrorState.isGone = false
                                binding.layoutStateHistory.ivErrorState.isGone = false
                                binding.layoutStateHistory.tvErrorState.isGone = false
                                binding.layoutStateHistory.tvErrorState.text =
                                    "Sorry, there's an error on the server"
                                binding.layoutStateHistory.ivErrorState.setImageResource(R.drawable.img_server_error)
                            }
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(this)) {
                            binding.layoutStateHistory.clErrorState.isGone = false
                            binding.layoutStateHistory.ivErrorState.isGone = false
                            binding.layoutStateHistory.tvErrorState.isGone = false
                            binding.layoutStateHistory.tvErrorState.text =
                                "Oops!\nYou're not connection"
                            binding.layoutStateHistory.ivErrorState.setImageResource(R.drawable.img_no_connection)
                        }
                    }
                }
            )
        }
    }

    private fun getData() {
        viewModel.getData()
    }

    companion object {
        const val EXTRA_DETAIL_COURSE_ID = "EXTRA_DETAIL_COURSE_ID"

        fun startActivity(context: Context) {
            val intent = Intent(context, HistoryPaymentActivity::class.java)
            context.startActivity(intent)
        }
    }
}
