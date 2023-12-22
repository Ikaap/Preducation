package com.kelompoksatuandsatu.preducation.presentation.feature.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.auth.otp.postemailotp.EmailOtp
import com.kelompoksatuandsatu.preducation.model.auth.otp.verifyotp.OtpData
import com.kelompoksatuandsatu.preducation.model.common.BaseResponse
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OtpViewModel(
    private val repo: UserRepository
) : ViewModel() {

    private val _otpResult = MutableLiveData<ResultWrapper<BaseResponse>>()
    val otpResult: LiveData<ResultWrapper<BaseResponse>>
        get() = _otpResult

    private val _emailOtpResult = MutableLiveData<ResultWrapper<Boolean>>()
    val emailOtpResult: LiveData<ResultWrapper<Boolean>>
        get() = _emailOtpResult

    fun userOtp(request: OtpData) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.verifyOtp(request).collect {
                _otpResult.postValue(it)
            }
        }
    }

    fun postEmailOtp(request: EmailOtp) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.postEmailOtp(request).collect {
                _emailOtpResult.postValue(it)
            }
        }
    }
}
