package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.user.UserViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepo: UserRepository) :
    ViewModel() {

    private val _getProfile = MutableLiveData<ResultWrapper<UserViewParam>>()
    val getProfile: LiveData<ResultWrapper<UserViewParam>>
        get() = _getProfile

    fun getUserById(userId: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getUserById(userId).collect {
                _getProfile.postValue(it)
            }
        }
    }

    fun performLogout() {
        viewModelScope.launch {
            try {
                userRepo.performLogout()
            } catch (e: Exception) {
            }
        }
    }
}
