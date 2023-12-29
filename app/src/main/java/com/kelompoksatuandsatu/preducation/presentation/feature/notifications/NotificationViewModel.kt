package com.kelompoksatuandsatu.preducation.presentation.feature.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.repository.NotificationRepository
import com.kelompoksatuandsatu.preducation.model.notification.NotificationItem
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: NotificationRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    private val _notifications = MutableLiveData<ResultWrapper<List<NotificationItem>>>()
    val notifications: LiveData<ResultWrapper<List<NotificationItem>>>
        get() = _notifications

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserNotifications().collect {
                _notifications.postValue(it)
            }
        }
    }

    private val _isUserLogin = MutableLiveData<Boolean>()
    val isUserLogin: LiveData<Boolean>
        get() = _isUserLogin

    fun checkLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val userStatus = userPreferenceDataSource.getUserToken().firstOrNull() != null
            _isUserLogin.postValue(userStatus)
        }
    }
}
