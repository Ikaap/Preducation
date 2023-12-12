 
package com.kelompoksatuandsatu.preducation.presentation.feature.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.network.api.model.user.UserRequest
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.launch

class EditProfileViewModel(private val userRepo: UserRepository) : ViewModel() {

    private val _changeProfileResult = MutableLiveData<ResultWrapper<Boolean>>()
    val changeProfileResult: LiveData<ResultWrapper<Boolean>>
        get() = _changeProfileResult

    suspend fun getUserById() = userRepo.getUserById()

    fun changeProfile(id: String, userRequest: UserRequest) {
        viewModelScope.launch {
            try {
                val result = userRepo.updateUserById(id, userRequest)
                _changeProfileResult.postValue(result)
            } catch (e: Exception) {
                // Handle exceptions if necessary
                _changeProfileResult.postValue(ResultWrapper.Error(e.message.toString()))
            }
        }
    }
}
