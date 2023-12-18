package com.kelompoksatuandsatu.preducation.presentation.feature.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.auth.OtpData
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OtpViewModel(
    private val repo: UserRepository
) : ViewModel() {

    private val _otpResult = MutableLiveData<ResultWrapper<Boolean>>()
    val otpResult: LiveData<ResultWrapper<Boolean>>
        get() = _otpResult

    fun userOtp(request: OtpData) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.userOtp(request).collect {
                _otpResult.postValue(it)
            }
        }
    }
}
