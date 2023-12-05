package com.kelompoksatuandsatu.preducation.presentation.feature.historypayment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
// import com.kelompoksatuandsatu.preducation.data.local.dummy.DummyPopularCourseDataSourceImpl
import com.kelompoksatuandsatu.preducation.databinding.ActivityTransactionBinding
import com.kelompoksatuandsatu.preducation.databinding.DialogNonLoginBinding
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.AdapterLayoutMenu
import com.kelompoksatuandsatu.preducation.presentation.common.adapter.CourseLinearListAdapter

class TransactionActivity : AppCompatActivity() {

    private val binding: ActivityTransactionBinding by lazy {
        ActivityTransactionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            onBackPressed()
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
