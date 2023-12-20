package com.kelompoksatuandsatu.preducation.presentation.feature.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.auth.UserAuth
import com.kelompoksatuandsatu.preducation.model.auth.UserRegisterResponse
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repo: UserRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _registerResult = MutableLiveData<ResultWrapper<UserRegisterResponse>>()
    val registerResult: LiveData<ResultWrapper<UserRegisterResponse>>
        get() = _registerResult

    private val _emailOtpResult = MutableLiveData<ResultWrapper<Boolean>>()
    val emailOtpResult: LiveData<ResultWrapper<Boolean>>
        get() = _emailOtpResult

    fun userRegister(request: UserAuth) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.userRegister(request).collect {
                _registerResult.postValue(it)
            }
        }
    }

    fun saveIdUser(id: String) {
        viewModelScope.launch {
            userPreferenceDataSource.saveUserId(id)
        }
    }
}
