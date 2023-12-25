package com.kelompoksatuandsatu.preducation.presentation.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.auth.login.LoginResponse
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.auth.UserLogin
import com.kelompoksatuandsatu.preducation.model.auth.UserLoginResponse
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.kelompoksatuandsatu.preducation.utils.exceptions.ApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repo: UserRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _loginResult = MutableLiveData<ResultWrapper<LoginResponse>>()
    val loginResult: LiveData<ResultWrapper<LoginResponse>>
        get() = _loginResult

    fun userLogin(request: UserLogin) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.userLogin(request).collect {
                    _loginResult.postValue(it)
                }
            } catch (e: ApiException) {
                _loginResult.postValue(ResultWrapper.Error(e))
            }
        }
    }

    fun saveIdUser(id: String) {
        viewModelScope.launch {
            userPreferenceDataSource.saveUserId(id)
        }
    }
}
