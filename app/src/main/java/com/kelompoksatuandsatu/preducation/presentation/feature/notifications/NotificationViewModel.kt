package com.kelompoksatuandsatu.preducation.presentation.feature.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompoksatuandsatu.preducation.data.repository.NotificationRepository
import com.kelompoksatuandsatu.preducation.model.notification.NotificationItem
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: NotificationRepository
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
}
