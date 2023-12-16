package com.kelompoksatuandsatu.preducation.presentation.feature.historypayment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSourceImpl
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.historyPaymentDataStore
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.PaymentDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.interceptor.AuthInterceptor
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import com.kelompoksatuandsatu.preducation.data.repository.PaymentRepository
import com.kelompoksatuandsatu.preducation.databinding.ActivityTransactionBinding
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.classprogress.HistoryPaymentListAdapter
import com.kelompoksatuandsatu.preducation.utils.PreferenceDataStoreHelperImpl
import kotlinx.coroutines.launch

class TransactionActivity : AppCompatActivity() {

    private val binding: ActivityTransactionBinding by lazy {
        ActivityTransactionBinding.inflate(layoutInflater)
    }

    private lateinit var historyPaymentAdapter: HistoryPaymentListAdapter
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var userPreferenceDataSource: UserPreferenceDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        historyPaymentAdapter = HistoryPaymentListAdapter { payment ->
            showSuccessDialog()
        }

        binding.rvHistory.adapter = historyPaymentAdapter
        val preferenceDataStoreHelper = PreferenceDataStoreHelperImpl(historyPaymentDataStore)

        userPreferenceDataSource = UserPreferenceDataSourceImpl(preferenceDataStoreHelper)
        val chucker = ChuckerInterceptor(this)
        val auth = AuthInterceptor(userPreferenceDataSource)

        val preducationService = PreducationService(chucker, auth)
        val paymentDataSource = PaymentDataSource(preducationService)
        val paymentRepository = PaymentRepository(paymentDataSource)
        val factory = PaymentViewModelFactory(paymentRepository)

        paymentViewModel = ViewModelProvider(this, factory)[PaymentViewModel::class.java]

        paymentViewModel.payments.observe(
            this,
            Observer { historyPaymentResponse ->
                val payments = historyPaymentResponse.data
                historyPaymentAdapter.setData(payments)
            }
        )

        lifecycleScope.launch {
            userPreferenceDataSource.getUserToken()
            paymentViewModel.fetchPayments()
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
