package com.kelompoksatuandsatu.preducation.data.local.datastore.datasource

import com.kelompoksatuandsatu.preducation.utils.PreferenceDataStoreHelper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserPreferenceDataSourceImplTest {

    @MockK
    lateinit var preferenceDataStoreHelper: PreferenceDataStoreHelper

    private lateinit var userPreferenceDataSource: UserPreferenceDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userPreferenceDataSource = UserPreferenceDataSourceImpl(preferenceDataStoreHelper)
    }

    @Test
    fun getUserToken() {
        runTest {
            coEvery { preferenceDataStoreHelper.getFirstPreference(any(), "") } returns ""
            val result = userPreferenceDataSource.getUserToken()
            coVerify { preferenceDataStoreHelper.getFirstPreference(any(), "") }
            assertEquals(result, "")
        }
    }

    @Test
    fun saveUserToken() {
        runTest {
            coEvery { preferenceDataStoreHelper.putPreference(any(), "") } returns Unit
            val result = userPreferenceDataSource.saveUserToken("")
            coVerify { preferenceDataStoreHelper.putPreference(any(), "") }
            assertEquals(result, Unit)
        }
    }

    @Test
    fun deleteAllData() {
        runTest {
            coEvery { preferenceDataStoreHelper.clearAllPreference() } returns Unit
            val result = userPreferenceDataSource.deleteAllData()
            coVerify { preferenceDataStoreHelper.clearAllPreference() }
            assertEquals(result, Unit)
        }
    }

    @Test
    fun saveUserId() {
        runTest {
            coEvery { preferenceDataStoreHelper.putPreference(any(), "") } returns Unit
            val result = userPreferenceDataSource.saveUserId("")
            coVerify { preferenceDataStoreHelper.putPreference(any(), "") }
            assertEquals(result, Unit)
        }
    }

    @Test
    fun getUserId() {
        runTest {
            coEvery { preferenceDataStoreHelper.getFirstPreference(any(), "") } returns ""
            val result = userPreferenceDataSource.getUserId()
            coVerify { preferenceDataStoreHelper.getFirstPreference(any(), "") }
            assertEquals(result, "")
        }
    }
}
