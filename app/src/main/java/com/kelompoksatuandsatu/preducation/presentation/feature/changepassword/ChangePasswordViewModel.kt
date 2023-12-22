package com.kelompoksatuandsatu.preducation.presentation.feature.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.changepassword.ChangePasswordRequest
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.user.Password
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangePasswordViewModel(
    private val repo: UserRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _updatedPassword =
        MutableLiveData<ResultWrapper<List<Password>>>()

    val updatedPassword: LiveData<ResultWrapper<List<Password>>>
        get() = _updatedPassword

    fun updatePassword(
        passwordRequest: ChangePasswordRequest
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userPreferenceDataSource.getUserId()
            repo.updateUserPassword(
                userId,
                passwordRequest
            ).collect {
                _updatedPassword.postValue(it)
            }
        }
    }
}
