package com.kelompoksatuandsatu.preducation.presentation.feature.historypayment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksatuandsatu.preducation.databinding.ActivityTransactionBinding
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.history.HistoryPaymentListAdapter
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
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
                    binding.rvHistory.apply {
                        isVisible = true
                        adapter = historyAdapter
                        layoutManager = LinearLayoutManager(this@HistoryPaymentActivity, RecyclerView.VERTICAL, false)
                    }
                    it.payload?.let {
                        historyAdapter.setData(it)
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
