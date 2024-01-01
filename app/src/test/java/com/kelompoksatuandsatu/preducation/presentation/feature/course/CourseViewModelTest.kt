package com.kelompoksatuandsatu.preducation.presentation.feature.course

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.Category
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.CreatedBy
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.category.categoryprogress.CategoryType
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
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

class CourseViewModelTest {

    @MockK
    private lateinit var courseRepo: CourseRepository

    @MockK
    private lateinit var userPreferenceDataSource: UserPreferenceDataSource

    @MockK
    private lateinit var assetWrapper: AssetWrapper

    private lateinit var viewModel: CourseViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = spyk(CourseViewModel(courseRepo, assetWrapper, userPreferenceDataSource))

        val resultListCourse = flow {
            emit(ResultWrapper.Success(listOf(CourseViewParam(Category("id", "name"), "CA", "Cilacap", CreatedBy("id", "name"), "desc", "id", true, "beginner", 100000, 1, listOf("mahasiswa"), "url", "title", 60.0, 10, 5.0, "free"))))
        }

        val resultListCategoriesType = flow {
            emit(ResultWrapper.Success(listOf(CategoryType("id", "name"))))
        }

        val resultListCategories = flow {
            emit(ResultWrapper.Success(listOf(CategoryClass("Cilacap", "id", "image", true, "name", 1))))
        }

        coEvery { assetWrapper.getString(any()) } returns ""
        coEvery { courseRepo.getCourseHomeFilter(any(), any(), any()) } returns resultListCourse
        coEvery { courseRepo.getCategoriesTypeClass() } returns resultListCategoriesType
        coEvery { courseRepo.getCategoriesClass() } returns resultListCategories
        coEvery { userPreferenceDataSource.getUserToken() } returns ""
        coEvery { courseRepo.getCategoriesClass() } returns resultListCategories
    }

    @Test
    fun `get course`() {
        viewModel.getCourse(listOf("android"), "premium", "title")
        coVerify { courseRepo.getCourseHomeFilter(any(), any(), any()) }
    }

    @Test
    fun `get categories type class`() {
        viewModel.getCategoriesTypeClass()
        coVerify { courseRepo.getCategoriesTypeClass() }
    }

    @Test
    fun `get categories class`() {
        viewModel.getCategories()
        coVerify { courseRepo.getCategoriesClass() }
    }

    @Test
    fun `check login user`() {
        viewModel.checkLogin()
        coVerify { userPreferenceDataSource.getUserToken() }
    }
}
