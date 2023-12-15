package com.kelompoksatuandsatu.preducation.presentation.feature.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.auth.UserForgotPassword
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForgotPasswordNewViewModel(private val repo: UserRepository) : ViewModel() {

    private val _forgotPasswordResult = MutableLiveData<ResultWrapper<Boolean>>()
    val forgotPasswordResult: LiveData<ResultWrapper<Boolean>>
        get() = _forgotPasswordResult

    fun userForgotPassword(request: UserForgotPassword) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.userForgotPassword(request).collect {
                _forgotPasswordResult.postValue(it)
            }
        }
    }
}
