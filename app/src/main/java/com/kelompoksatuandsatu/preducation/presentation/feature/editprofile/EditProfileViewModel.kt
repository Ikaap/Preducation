package com.kelompoksatuandsatu.preducation.presentation.feature.editprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.user.UserViewParam
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditProfileViewModel(
    private val userRepo: UserRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _getProfile = MutableLiveData<ResultWrapper<UserViewParam>>()
    val getProfile: LiveData<ResultWrapper<UserViewParam>>
        get() = _getProfile

    private val _updateProfile = MutableLiveData<ResultWrapper<UserViewParam>>()
    val updateProfileResult: LiveData<ResultWrapper<UserViewParam>>
        get() = _updateProfile

    fun getUserById() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userPreferenceDataSource.getUserId()
            Log.d("USER ID EDIT PROFILE", "getUserById: $userId")
            userRepo.getUserById(userId).collect {
                _getProfile.postValue(it)
            }
        }
    }

    fun updateProfile(
        name: RequestBody?,
        email: RequestBody?,
        phone: RequestBody?,
        country: RequestBody?,
        city: RequestBody?,
        imageProfile: MultipartBody.Part?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userPreferenceDataSource.getUserId()
            Log.d("USER ID EDIT PROFILE", "getUserById: $userId")
            userRepo.updateUserById(
                userId,
                name,
                email,
                phone,
                country,
                city,
                imageProfile
            ).collect {
                _updateProfile.postValue(it)
            }
        }
    }
}
