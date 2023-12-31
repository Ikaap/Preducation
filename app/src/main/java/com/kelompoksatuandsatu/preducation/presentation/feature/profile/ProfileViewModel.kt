package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.user.UserViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepo: UserRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) :
    ViewModel() {

    private val _getProfile = MutableLiveData<ResultWrapper<UserViewParam>>()
    val getProfile: LiveData<ResultWrapper<UserViewParam>>
        get() = _getProfile

    private val _logoutResults = MutableLiveData<ResultWrapper<Boolean>>()
    val logoutResults: LiveData<ResultWrapper<Boolean>>
        get() = _logoutResults

    private val _isUserLogin = MutableLiveData<Boolean>()
    val isUserLogin: LiveData<Boolean>
        get() = _isUserLogin

    fun getUserById() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userPreferenceDataSource.getUserId()
            userRepo.getUserById(userId).collect {
                _getProfile.postValue(it)
            }
        }
    }

    fun userLogout() {
        viewModelScope.launch {
            _logoutResults.value = userRepo.userLogout().first()
        }
    }

    fun checkLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val userStatus = userPreferenceDataSource.getUserToken().firstOrNull() != null
            _isUserLogin.postValue(userStatus)
        }
    }
}
