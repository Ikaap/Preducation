package com.kelompoksatuandsatu.preducation.presentation.feature.payment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import coil.load
import com.kelompoksatuandsatu.preducation.databinding.ActivityPaymentBinding
import com.kelompoksatuandsatu.preducation.databinding.LayoutDialogSuccessPaymentBinding
import com.kelompoksatuandsatu.preducation.model.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.main.MainActivity
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import com.kelompoksatuandsatu.preducation.utils.toCurrencyFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PaymentActivity : AppCompatActivity() {

    private val binding: ActivityPaymentBinding by lazy {
        ActivityPaymentBinding.inflate(layoutInflater)
    }

    private val viewModel: PaymentViewModel by viewModel {
        parametersOf(intent.extras ?: bundleOf())
    }

    private var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListener()
        getDataCourse(viewModel.course)
        observeData()
    }

    private fun setClickListener() {
        binding.clButtonPayment.setOnClickListener {
            viewModel.payment()
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getDataCourse(detailCourse: DetailCourseViewParam?) {
        detailCourse?.let {
            binding.ivBannerCourse.load(detailCourse.thumbnail)
            binding.tvCategoryCourse.text = detailCourse.category?.name
            binding.tvCourseRating.text = detailCourse.totalRating.toString()
            binding.tvNameCourse.text = detailCourse.title
            binding.tvTotalModulCourse.text = detailCourse.totalModule.toString() + " Module"
            binding.tvTotalHourCourse.text = detailCourse.totalDuration.toString() + " Mins"
            binding.tvLevelCourse.text = detailCourse.level + " Level"
            binding.tvCoursePrice.text = detailCourse.price?.toCurrencyFormat()
            // ppn ada tidak ?
        }
    }

    private fun observeData() {
        viewModel.paymentResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let {
                        Toast.makeText(
                            this,
                            "token : ${it.token}, url : ${it.redirectUrl}",
                            Toast.LENGTH_SHORT
                        ).show()
                        url = it.redirectUrl.orEmpty()
                        openUrlFromWebView(url)
                    }
                },
                doOnError = { e ->
                    Toast.makeText(this, "error: ${e.exception?.message}", Toast.LENGTH_SHORT)
                        .show()
                },
                doOnEmpty = {
                    Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openUrlFromWebView(url: String) {
        binding.webViewMidtrans.isGone = false
        binding.webViewMidtrans.webViewClient = object : WebViewClient() {
            val prosessDialog = ProgressDialog(this@PaymentActivity)
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val reqUrl = request?.url.toString()
                return if (reqUrl.contains("gojek://") ||
                    reqUrl.contains("shopeeid://") ||
                    reqUrl.contains("//wsa.wallet.airpay.co.id/") ||
                    reqUrl.contains("/gopay/partner/") ||
                    reqUrl.contains("/shopeepay/")
                ) {
                    val intent = Intent(Intent.ACTION_VIEW, request?.url)
                    startActivity(intent)
                    true
                } else {
                    false
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                prosessDialog.setMessage("Loading...")
                prosessDialog.show()
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                prosessDialog.hide()
                Log.d("PaymentActivity", "onPageFinished: $url")
                if (url?.contains("success") == true) {
                    showSuccessDialog()
                }
                super.onPageFinished(view, url)
            }
        }
        binding.webViewMidtrans.settings.loadsImagesAutomatically = true
        binding.webViewMidtrans.settings.javaScriptEnabled = true
        binding.webViewMidtrans.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        binding.webViewMidtrans.loadUrl(url)
    }

    private fun showSuccessDialog() {
        val binding: LayoutDialogSuccessPaymentBinding =
            LayoutDialogSuccessPaymentBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this, 0).create()

        binding.tvBackToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.clButtonStartStudy.setOnClickListener {
            val intent = Intent(this, DetailClassActivity::class.java)
            startActivity(intent)
        }

        dialog.apply {
            setView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()
    }

    companion object {
        const val EXTRA_DETAIL_COURSE = "EXTRA_DETAIL_COURSE"
    }
}
