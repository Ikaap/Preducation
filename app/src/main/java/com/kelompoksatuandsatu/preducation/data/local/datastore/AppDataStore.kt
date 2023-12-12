package com.kelompoksatuandsatu.preducation.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.appDataStore by preferencesDataStore(
    name = "PreducationDataStore"
)