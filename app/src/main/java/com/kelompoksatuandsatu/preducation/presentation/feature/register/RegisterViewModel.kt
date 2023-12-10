package com.kelompoksatuandsatu.preducation.presentation.feature.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.auth.UserAuth
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: UserRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<ResultWrapper<String>>()
    val registerResult: LiveData<ResultWrapper<String>>
        get() = _registerResult

    fun userRegister(request: UserAuth) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.userRegister(request).collect {
                _registerResult.postValue(it)
            }
        }
    }
}
