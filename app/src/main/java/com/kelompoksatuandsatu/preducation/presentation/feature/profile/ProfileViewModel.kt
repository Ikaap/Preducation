package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(private val repo: UserRepository) : ViewModel() {
    private val _logoutResults = MutableLiveData<ResultWrapper<Boolean>>()
    val logoutResults: LiveData<ResultWrapper<Boolean>>
        get() = _logoutResults

    fun userLogout() {
        viewModelScope.launch {
            _logoutResults.value = repo.userLogout().first()
        }
    }
}
