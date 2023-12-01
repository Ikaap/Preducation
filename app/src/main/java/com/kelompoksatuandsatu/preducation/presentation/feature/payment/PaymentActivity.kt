package com.kelompoksatuandsatu.preducation.presentation.feature.payment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.kelompoksatuandsatu.preducation.databinding.ActivityPaymentBinding
import com.kelompoksatuandsatu.preducation.databinding.LayoutDialogSuccessPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private val binding: ActivityPaymentBinding by lazy {
        ActivityPaymentBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setLayoutDetailPayment()
        setClickListener()
    }

    private fun setLayoutDetailPayment() {
        binding.ivArrowDownBank.setOnClickListener {
            binding.ivArrowDownBank.isGone = true
            binding.ivArrowUpBank.isGone = false
            binding.layoutDetailPaymentBankTransfer.root.isGone = false
        }

        binding.ivArrowUpBank.setOnClickListener {
            binding.ivArrowDownBank.isGone = false
            binding.ivArrowUpBank.isGone = true
            binding.layoutDetailPaymentBankTransfer.root.isGone = true
        }

        binding.ivArrowDownCredit.setOnClickListener {
            binding.ivArrowDownCredit.isGone = true
            binding.ivArrowUpCredit.isGone = false
            binding.layoutDetailPaymentCreditCard.root.isGone = false
        }

        binding.ivArrowUpCredit.setOnClickListener {
            binding.ivArrowDownCredit.isGone = false
            binding.ivArrowUpCredit.isGone = true
            binding.layoutDetailPaymentCreditCard.root.isGone = true
        }
    }
    private fun setClickListener() {
        binding.clButtonPayment.setOnClickListener {
            showSuccessDialog()
        }
    }

    private fun showSuccessDialog() {
        val binding: LayoutDialogSuccessPaymentBinding = LayoutDialogSuccessPaymentBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this, 0).create()

        dialog.apply {
            setView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()
    }
}
