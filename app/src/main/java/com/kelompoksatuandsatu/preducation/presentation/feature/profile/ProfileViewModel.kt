package com.kelompoksatuandsatu.preducation.presentation.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepo: UserRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    fun deleteDataUser() {
        viewModelScope.launch {
            userPreferenceDataSource.deleteAllData()
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
