package com.kelompoksatuandsatu.preducation.presentation.feature.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserRequest
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.UserViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel(private val userRepo: UserRepository) :
    ViewModel() {

    private val _getProfile = MutableLiveData<ResultWrapper<List<UserViewParam>>>()
    val getProfile: LiveData<ResultWrapper<List<UserViewParam>>>
        get() = _getProfile

    fun getUserById(userId: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getUserById(userId).collect {
                _getProfile.postValue(it)
            }
        }
    }

    fun updateProfile(
        userId: String,
        userRequest: UserRequest
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.updateUserById(userId, userRequest).collect {
                _getProfile.postValue(it)
            }
        }
    }
}
