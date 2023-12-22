package com.kelompoksatuandsatu.preducation.data.local.datastore.datasource

import androidx.datastore.preferences.core.stringPreferencesKey
import com.kelompoksatuandsatu.preducation.utils.PreferenceDataStoreHelper

interface UserPreferenceDataSource {

    suspend fun getUserToken(): String
    suspend fun saveUserToken(token: String)

    suspend fun deleteAllData()

    suspend fun saveUserId(id: String)
    suspend fun getUserId(): String
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

    override suspend fun saveUserId(id: String) {
        return preferenceHelper.putPreference(USER_ID_KEY, id)
    }

    override suspend fun getUserId(): String {
        return preferenceHelper.getFirstPreference(USER_ID_KEY, "")
    }

    companion object {
        val USER_TOKEN_KEY = stringPreferencesKey("USER_TOKEN_KEY")
        val USER_ID_KEY = stringPreferencesKey("USER_ID_KEY")
    }
}
