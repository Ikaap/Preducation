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
import com.kelompoksatuandsatu.preducation.databinding.ActivityTransactionBinding
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.history.HistoryPaymentListAdapter
import com.kelompoksatuandsatu.preducation.presentation.feature.login.LoginActivity
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryPaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionBinding

    private val viewModel: HistoryPaymentViewModel by viewModel()

    private val historyAdapter: HistoryPaymentListAdapter by lazy {
        HistoryPaymentListAdapter {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.checkLogin()
        viewModel.isUserLogin.observe(this) { isLogin ->
            if (!isLogin) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

                return@observe
            }
        }

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
            layoutManager = LinearLayoutManager(this@HistoryPaymentActivity, RecyclerView.VERTICAL, false)
            adapter = historyAdapter
        }
    }

    private fun observeData() {
        viewModel.payment.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutCommonState.root.isGone = true
                    binding.layoutCommonState.tvError.isGone = true
                    binding.layoutCommonState.tvDataEmpty.isGone = true
                    binding.layoutCommonState.ivDataEmpty.isGone = true
                    binding.layoutCommonState.clServerError.isGone = true
                    binding.layoutCommonState.ivServerError.isGone = true
                    binding.layoutCommonState.clNoConnection.isGone = true
                    binding.layoutCommonState.ivNoConnection.isGone = true

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
                    binding.root.isVisible = true
                    binding.rvHistory.isVisible = false
                    binding.layoutCommonState.root.isGone = true
                    binding.layoutCommonState.clDataEmpty.isGone = true
                    binding.layoutCommonState.tvError.isGone = true
                    binding.layoutCommonState.tvDataEmpty.isGone = true
                    binding.layoutCommonState.ivDataEmpty.isGone = true
                    binding.layoutCommonState.clServerError.isGone = true
                    binding.layoutCommonState.ivServerError.isGone = true
                    binding.layoutCommonState.clNoConnection.isGone = true
                    binding.layoutCommonState.ivNoConnection.isGone = true
                },
                doOnEmpty = {
                    binding.layoutCommonState.root.isGone = false
                    binding.layoutCommonState.clDataEmpty.isGone = true
                    binding.layoutCommonState.tvError.isGone = false
                    binding.layoutCommonState.tvError.text = "data kosong"
                    binding.layoutCommonState.tvDataEmpty.isGone = true
                    binding.layoutCommonState.ivDataEmpty.isGone = true
                    binding.layoutCommonState.clServerError.isGone = true
                    binding.layoutCommonState.ivServerError.isGone = true
                    binding.layoutCommonState.clNoConnection.isGone = true
                    binding.layoutCommonState.ivNoConnection.isGone = true
                },
                doOnError = {
                    binding.root.isVisible = true
                    binding.rvHistory.isVisible = false
                    binding.layoutCommonState.root.isGone = false
                    binding.layoutCommonState.clDataEmpty.isGone = true
                    binding.layoutCommonState.tvError.isGone = false
                    binding.layoutCommonState.tvError.text = it.exception?.message.toString()
                    binding.layoutCommonState.tvDataEmpty.isGone = true
                    binding.layoutCommonState.ivDataEmpty.isGone = true

                    if (it.exception is ApiException) {
                        if (it.exception.getParsedErrorHistoryPayment()?.success == false) {
                            if (it.exception.httpCode == 500) {
                                binding.layoutCommonState.clServerError.isGone = false
                                binding.layoutCommonState.ivServerError.isGone = false
                                StyleableToast.makeText(
                                    this,
                                    "SERVER ERROR",
                                    R.style.failedtoast
                                ).show()
                            } else if (it.exception.getParsedErrorHistoryPayment()?.success == false) {
                                binding.layoutCommonState.tvError.text =
                                    it.exception.getParsedErrorHistoryPayment()?.message
                                StyleableToast.makeText(
                                    this,
                                    it.exception.getParsedErrorHistoryPayment()?.message,
                                    R.style.failedtoast
                                ).show()
                            }
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(this)) {
                            binding.layoutCommonState.clNoConnection.isGone = false
                            binding.layoutCommonState.ivNoConnection.isGone = false
                            StyleableToast.makeText(
                                this,
                                "tidak ada internet",
                                R.style.failedtoast
                            ).show()
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
        fun startActivity(context: Context) {
            val intent = Intent(context, HistoryPaymentActivity::class.java)
            context.startActivity(intent)
        }
    }
}
