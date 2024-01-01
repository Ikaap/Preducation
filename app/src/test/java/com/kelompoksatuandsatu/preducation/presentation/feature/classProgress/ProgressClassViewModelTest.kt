package com.kelompoksatuandsatu.preducation.presentation.feature.classProgress

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.Category
import com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.CourseId
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.category.categoryprogress.CategoryType
import com.kelompoksatuandsatu.preducation.model.progress.CourseProgressItemClass
import com.kelompoksatuandsatu.preducation.utils.AssetWrapper
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import com.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProgressClassViewModelTest {
    @MockK
    private lateinit var courseRepo: CourseRepository

    @MockK
    private lateinit var userPreferenceDataSource: UserPreferenceDataSource

    @MockK
    private lateinit var assetWrapper: AssetWrapper

    private lateinit var viewModel: ProgressClassViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProgressClassViewModel(courseRepo, assetWrapper, userPreferenceDataSource))

        val resultListCourseProgress = flow {
            emit(ResultWrapper.Success(listOf(CourseProgressItemClass("Cilacap", 90, 1, 3, "id", true, "user id", CourseId(60, "Class code", 1, "url", "beginner", "desc", 4.5, "title", true, 20, "Cilacap", listOf("Mahasiswa"), 150000, "Premium", "id", com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.Category("name", "id", "url")), "Done"))))
        }

        val resultListCategories = flow {
            emit(ResultWrapper.Success(listOf(CategoryClass("Cilacap", "id", "image", true, "name", 1))))
        }

        val resultListCategoriesProgress = flow {
            emit(ResultWrapper.Success(listOf(CategoryType("id", "name"), CategoryType("id", "name"))))
        }
        coEvery { assetWrapper.getString(any()) } returns ""
        coEvery { courseRepo.getCourseUserProgress(any()) } returns resultListCourseProgress
        coEvery { courseRepo.getCategoriesClass() } returns resultListCategories
        coEvery { courseRepo.getCategoriesProgress() } returns resultListCategoriesProgress
        coEvery { userPreferenceDataSource.getUserToken() } returns ""
    }

    @Test
    fun `get course progress`() {
        viewModel.getCourseProgress("premium")
        coVerify { courseRepo.getCourseUserProgress(any()) }
    }

    @Test
    fun `get categories class`() {
        viewModel.getCategoriesClass()
        coVerify { courseRepo.getCategoriesClass() }
    }

    @Test
    fun `get categories class progress`() {
        viewModel.getCategoriesProgress()
        coVerify { courseRepo.getCategoriesProgress() }
    }

    @Test
    fun `check login user`() {
        viewModel.checkLogin()
        coVerify { userPreferenceDataSource.getUserToken() }
    }
}
