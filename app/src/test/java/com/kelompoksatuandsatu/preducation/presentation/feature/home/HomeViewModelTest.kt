package com.kelompoksatuandsatu.preducation.presentation.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kelompoksatuandsatu.preducation.data.local.datastore.datasource.UserPreferenceDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.Category
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.CreatedBy
import com.kelompoksatuandsatu.preducation.data.repository.CourseRepository
import com.kelompoksatuandsatu.preducation.data.repository.UserRepository
import com.kelompoksatuandsatu.preducation.model.category.categoryclass.CategoryClass
import com.kelompoksatuandsatu.preducation.model.course.courseall.CourseViewParam
import com.kelompoksatuandsatu.preducation.model.user.UserViewParam
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

class HomeViewModelTest {

    @MockK
    private lateinit var courseRepo: CourseRepository

    @MockK
    private lateinit var userPreferenceDataSource: UserPreferenceDataSource

    @MockK
    private lateinit var userRepo: UserRepository

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = spyk(HomeViewModel(courseRepo, userPreferenceDataSource, userRepo))

        val resultUser = flow {
            emit(
                ResultWrapper.Success(
                    UserViewParam(
                        "id",
                        "email",
                        "089",
                        "name",
                        "image",
                        "country",
                        "city"
                    )
                )
            )
        }

        val resultListCategories = flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        CategoryClass(
                            "Cilacap",
                            "id",
                            "image",
                            true,
                            "name",
                            1
                        )
                    )
                )
            )
        }

        val resultListCourse = flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        CourseViewParam(
                            Category("id", "name"),
                            "CA",
                            "Cilacap",
                            CreatedBy("id", "name"),
                            "desc",
                            "id",
                            true,
                            "beginner",
                            100000,
                            1,
                            listOf("mahasiswa"),
                            "url",
                            "title",
                            60.0,
                            10,
                            5.0,
                            "free"
                        )
                    )
                )
            )
        }

        coEvery { courseRepo.getCategoriesClass() } returns resultListCategories
        coEvery { courseRepo.getCategoriesClass() } returns resultListCategories
        coEvery { courseRepo.getCourseHome(any(), any(), any()) } returns resultListCourse
        coEvery { userPreferenceDataSource.getUserToken() } returns ""
        coEvery { userRepo.getUserById(any()) } returns resultUser
        coEvery { userPreferenceDataSource.getUserId() } returns ""
    }

    @Test
    fun `get categories class`() {
        viewModel.getCategoriesClass()
        coVerify { courseRepo.getCategoriesClass() }
    }

    @Test
    fun `get categories class popular`() {
        viewModel.getCategoriesClassPopular()
        coVerify { courseRepo.getCategoriesClass() }
    }

    @Test
    fun `get course home`() {
        viewModel.getCourse("android", "free", "belajar")
        coVerify { courseRepo.getCourseHome(any(), any(), any()) }
    }

    @Test
    fun `check login user`() {
        viewModel.checkLogin()
        coVerify { userPreferenceDataSource.getUserToken() }
    }

    @Test
    fun `get user by id`() {
        viewModel.getUserById()
        coVerify { userRepo.getUserById(any()) }
    }
}
