package com.kelompoksatuandsatu.preducation.presentation.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.auth.UserLogin
import com.kelompoksatuandsatu.preducation.model.auth.UserLoginResponse
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repo: UserRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _loginResult = MutableLiveData<ResultWrapper<UserLoginResponse>>()
    val loginResult: LiveData<ResultWrapper<UserLoginResponse>>
        get() = _loginResult

    fun userLogin(request: UserLogin) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.userLogin(request).collect {
                _loginResult.postValue(it)
            }
        }
    }

    fun saveIdUser(id: String) {
        viewModelScope.launch {
            userPreferenceDataSource.saveUserId(id)
        }
    }
}
