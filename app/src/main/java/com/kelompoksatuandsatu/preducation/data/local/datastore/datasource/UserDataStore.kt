package com.kelompoksatuandsatu.preducation.data.local.datastore.datasource

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.userDataStore by preferencesDataStore(
    name = "userPreference"
)
