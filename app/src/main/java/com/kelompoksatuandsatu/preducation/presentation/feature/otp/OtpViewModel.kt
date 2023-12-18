package com.kelompoksatuandsatu.preducation.presentation.feature.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper

class OtpViewModel(
    private val repo: UserRepository
) : ViewModel() {

    private val _otpResult = MutableLiveData<ResultWrapper<Boolean>>()
    val otpResult: LiveData<ResultWrapper<Boolean>>
        get() = _otpResult
}
