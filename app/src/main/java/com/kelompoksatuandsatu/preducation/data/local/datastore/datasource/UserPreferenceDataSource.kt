package com.kelompoksatuandsatu.preducation.data.local.datastore.datasource

import androidx.datastore.preferences.core.stringPreferencesKey
import com.kelompoksatuandsatu.preducation.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {

    suspend fun getUserToken(): String
    suspend fun saveUserToken(token: String)

    suspend fun deleteAllData()
}

class UserPreferenceDataSourceImpl(
    private val preferenceHelper: PreferenceDataStoreHelper
) : UserPreferenceDataSource {

    override suspend fun getUserToken(): String {
        return preferenceHelper.getFirstPreference(USER_TOKEN_KEY, "")
    }

    override suspend fun saveUserToken(token: String) {
        return preferenceHelper.putPreference(USER_TOKEN_KEY, token)
    }

    override suspend fun deleteAllData() {
        preferenceHelper.clearAllPreference()
    }

    companion object {
        val USER_TOKEN_KEY = stringPreferencesKey("USER_TOKEN_KEY")
    }
}
