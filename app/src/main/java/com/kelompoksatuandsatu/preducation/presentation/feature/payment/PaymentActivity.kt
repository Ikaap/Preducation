package com.kelompoksatuandsatu.preducation.presentation.feature.payment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.kelompoksatuandsatu.preducation.R
import com.kelompoksatuandsatu.preducation.databinding.ActivityPaymentBinding
import com.kelompoksatuandsatu.preducation.databinding.LayoutDialogSuccessPaymentBinding
import com.kelompoksatuandsatu.preducation.model.auth.otp.postemailotp.EmailOtp
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.presentation.feature.detailclass.DetailClassActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.main.MainActivity
import com.kelompoksatuandsatu.preducation.presentation.feature.otp.OtpActivity
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import com.kelompoksatuandsatu.preducation.utils.exceptions.NoInternetException
import com.kelompoksatuandsatu.preducation.utils.proceedWhen
import com.kelompoksatuandsatu.preducation.utils.toCurrencyFormat
import io.github.muddz.styleabletoast.StyleableToast
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

    private var emailData = EmailOtp("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getEmail()
        setClickListener()
        getDataCourse(viewModel.course)
        observeData()
    }

    private fun getEmail() {
        viewModel.getUserById()
    }

    private fun setClickListener() {
        binding.clButtonPayment.setOnClickListener {
            viewModel.payment()
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDataCourse(detailCourse: DetailCourseViewParam?) {
        detailCourse?.let {
            binding.ivBannerCourse.load(detailCourse.thumbnail)
            binding.tvCategoryCourse.text = detailCourse.category?.name
            binding.tvCourseRating.text = detailCourse.totalRating.toString()
            binding.tvNameCourse.text = detailCourse.title
            binding.tvTotalModulCourse.text = detailCourse.totalModule.toString() + getString(R.string.text_module)
            binding.tvTotalHourCourse.text = detailCourse.totalDuration.toString() + getString(R.string.text_mins)
            binding.tvLevelCourse.text = detailCourse.level + getString(R.string.text_level)
            binding.tvCoursePrice.text = detailCourse.price?.toCurrencyFormat()
            val ppnValue = it.price?.times(0.11)!!.toInt()
            binding.tvCoursePpn.text = ppnValue.toCurrencyFormat()
            val totalPayment = it.price + ppnValue
            binding.tvCourseTotalPayment.text = totalPayment.toCurrencyFormat()
        }
    }

    private fun observeData() {
        viewModel.getUserData.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload.let {
                        emailData = EmailOtp(it?.email)
                    }
                }
            )
        }
        viewModel.paymentResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutStatePayment.root.isVisible = false
                    binding.layoutStatePayment.tvError.isVisible = false
                    binding.layoutStatePayment.pbLoading.isVisible = false
                    it.payload?.let {
                        url = it.data?.redirectUrl.orEmpty()
                        openUrlFromWebView(url)
                    }
                },
                doOnError = { e ->
                    binding.layoutStatePayment.root.isVisible = true
                    binding.layoutStatePayment.tvError.isVisible = true
                    binding.layoutStatePayment.tvError.text = e.exception?.message
                    binding.layoutStatePayment.pbLoading.isVisible = false

                    if (it.exception is ApiException) {
                        if (it.exception.httpCode == 400) {
                            StyleableToast.makeText(
                                this,
                                it.exception.getParsedErrorPayment()?.message + getString(R.string.text_please_check_your_email_to_get_the_otp_code),
                                R.style.failedtoast
                            ).show()

                            viewModel.postEmailOtp(emailData)
                            val intent = Intent(this, OtpActivity::class.java)
                            startActivity(intent)
                        } else if (it.exception.httpCode == 500) {
                            binding.layoutStatePayment.clErrorState.isGone = false
                            binding.layoutStatePayment.ivErrorState.isGone = false
                            binding.layoutStatePayment.tvErrorState.isGone = false
                            binding.layoutStatePayment.tvErrorState.text =
                                getString(R.string.text_sorry_there_s_an_error_on_the_server)
                            binding.layoutStatePayment.ivErrorState.setImageResource(R.drawable.img_server_error)
                        }
                    } else if (it.exception is NoInternetException) {
                        if (!it.exception.isNetworkAvailable(this)) {
                            binding.layoutStatePayment.clErrorState.isGone = false
                            binding.layoutStatePayment.ivErrorState.isGone = false
                            binding.layoutStatePayment.tvErrorState.isGone = false
                            binding.layoutStatePayment.tvErrorState.text =
                                getString(R.string.text_no_internet_connection)
                            binding.layoutStatePayment.ivErrorState.setImageResource(R.drawable.img_no_connection)
                        }
                    }
                },
                doOnEmpty = {
                    binding.layoutStatePayment.root.isVisible = true
                    binding.layoutStatePayment.tvError.isVisible = true
                    binding.layoutStatePayment.tvError.text = it.exception?.message
                    binding.layoutStatePayment.pbLoading.isVisible = false
                },
                doOnLoading = {
                    binding.layoutStatePayment.root.isVisible = true
                    binding.layoutStatePayment.tvError.isVisible = false
                    binding.layoutStatePayment.pbLoading.isVisible = true
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
                prosessDialog.setMessage(getString(R.string.text_loading))
                prosessDialog.show()
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                prosessDialog.hide()
                if (url?.contains(getString(R.string.text_success)) == true) {
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
            viewModel.getCourseById()
            observeDataDetailClass()
        }

        dialog.apply {
            setView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()
    }

    private fun observeDataDetailClass() {
        viewModel.detailCourse.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let {
                        val intent = Intent(this, DetailClassActivity::class.java)
                        intent.putExtra(EXTRA_DETAIL_COURSE_ID, it.id)
                        startActivity(intent)
                    }
                }
            )
        }
    }

    companion object {
        const val EXTRA_DETAIL_COURSE = "EXTRA_DETAIL_COURSE"
        const val EXTRA_DETAIL_COURSE_ID = "EXTRA_DETAIL_COURSE_ID"
    }
}
