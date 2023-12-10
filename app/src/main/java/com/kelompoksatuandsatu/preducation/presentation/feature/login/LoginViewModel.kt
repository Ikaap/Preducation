package com.kelompoksatuandsatu.preducation.presentation.feature.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.auth.LoginToken
import com.kelompoksatuandsatu.preducation.model.auth.UserLogin
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: UserRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<ResultWrapper<LoginToken>>()
    val loginResult: LiveData<ResultWrapper<LoginToken>>
        get() = _loginResult

    fun userLogin(request: UserLogin) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.userLogin(request).collect {
                Log.e("Login Token : ", "${it.payload?.accessToken}")
                _loginResult.postValue(it)
            }
        }
    }
}
