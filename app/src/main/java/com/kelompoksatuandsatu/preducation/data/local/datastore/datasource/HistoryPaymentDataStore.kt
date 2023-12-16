package com.kelompoksatuandsatu.preducation.data.local.datastore.datasource

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.historyPaymentDataStore by preferencesDataStore(
    name = "historyPaymentPreference"
)
