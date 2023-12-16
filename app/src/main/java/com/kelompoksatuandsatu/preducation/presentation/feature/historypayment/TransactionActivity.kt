package com.kelompoksatuandsatu.preducation.presentation.feature.historypayment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kelompoksatuandsatu.preducation.data.repository.PaymentRepository
import com.kelompoksatuandsatu.preducation.databinding.ActivityTransactionBinding
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.classprogress.HistoryPaymentListAdapter

class TransactionActivity : AppCompatActivity() {

    private val binding: ActivityTransactionBinding by lazy {
        ActivityTransactionBinding.inflate(layoutInflater)
    }

    private lateinit var historyPaymentAdapter: HistoryPaymentListAdapter
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

//        historyPaymentAdapter = HistoryPaymentListAdapter { payment ->
//            showSuccessDialog()
//        }
//
//        binding.rvHistory.adapter = historyPaymentAdapter
//        val preferenceDataStoreHelper = PreferenceDataStoreHelperImpl(historyPaymentDataStore)
//
//        val userPreferenceDataSource = UserPreferenceDataSourceImpl(preferenceDataStoreHelper)
//        val chucker = ChuckerInterceptor(this)
//        val auth = AuthInterceptor(userPreferenceDataSource)
//
//        val preducationService = PreducationService(chucker, auth)
//        val paymentDataSource = PaymentDataSource(preducationService)
//        val paymentRepository = PaymentRepository(paymentDataSource)
//        val factory = PaymentViewModelFactory(paymentRepository)
//
//        paymentViewModel = ViewModelProvider(this, factory)[PaymentViewModel::class.java]
//
//        paymentViewModel.payments.observe(
//            this,
//            Observer { payments ->
//                historyPaymentAdapter.setData(payments)
//            }
//        )
//
//        paymentViewModel.fetchPayments("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NTc5YTI3MzQ3NjEwMDFmOWU0OGRmNGEiLCJlbWFpbCI6InVzZXIxQGV4YW1wbGUuY29tIiwicGhvbmUiOiIxMjM0NTY3ODkwIiwicm9sZSI6InVzZXIiLCJpYXQiOjE3MDI1NjkzNjYsImV4cCI6MTcwMjgyODU2Nn0.HBZWsz06YnY411Wh-ASPNhvhgOSzda74Js3FahKx-nI")
    }

    private fun showSuccessDialog() {
        val binding: DialogNonLoginBinding = DialogNonLoginBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this, 0).create()

        dialog.apply {
            setView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()
    }
}

class PaymentViewModelFactory(private val repository: PaymentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaymentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
