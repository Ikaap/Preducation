package com.kelompoksatuandsatu.preducation.data.local.datastore.datasource

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kelompoksatuandsatu.preducation.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {

    fun getUserToken(): Flow<String>
    suspend fun saveUserToken(token: String)
}

class UserPreferenceDataSourceImpl(
    private val preferenceHelper: PreferenceDataStoreHelper
) : UserPreferenceDataSource {

    override fun getUserToken(): Flow<String> {
        return preferenceHelper.getPreference(USER_TOKEN_KEY, "null")
    }

    override suspend fun saveUserToken(token: String) {
        return preferenceHelper.putPreference(USER_TOKEN_KEY, token)
    }

    companion object {
        val USER_TOKEN_KEY = stringPreferencesKey("USER_TOKEN_KEY")
    }

}