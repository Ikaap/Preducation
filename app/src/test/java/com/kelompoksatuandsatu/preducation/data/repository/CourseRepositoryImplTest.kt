package com.kelompoksatuandsatu.preducation.data.repository

import app.cash.turbine.test
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.CategoriesClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.Data
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.Category
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.CourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.CreatedBy
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.DataCourseAll
import com.kelompoksatuandsatu.preducation.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CourseRepositoryImplTest {

    @MockK
    lateinit var courseDataSource: CourseDataSource

    private lateinit var courseRepo: CourseRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        courseRepo = CourseRepositoryImpl(courseDataSource)
    }

    @Test
    fun `get categories class, result success`() {
        val fakeCategoryClassResponse = Data(
            createdAt = "Cilacap",
            id = "c1",
            imageCategory = "url",
            isActive = false,
            name = "All",
            v = 1
        )

        val fakeCategoriesClassResponse = CategoriesClassResponse(
            data = listOf(fakeCategoryClassResponse),
            message = "success",
            success = true

        )
        runTest {
            coEvery { courseDataSource.getCategoriesClass() } returns fakeCategoriesClassResponse
            courseRepo.getCategoriesClass().map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.name, "All")
                coVerify { courseDataSource.getCategoriesClass() }
            }
        }
    }

    fun `get categories class, result loading`() {
        val mockCategoriesClassResponse = mockk<CategoriesClassResponse>()
        runTest {
            coEvery { courseDataSource.getCategoriesClass() } returns mockCategoriesClassResponse
            courseRepo.getCategoriesClass().map {
                delay(100)
                it
            }.test {
                delay(130)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { courseDataSource.getCategoriesClass() }
            }
        }
    }

    @Test
    fun `get categories class, result empty`() {
        val fakeCategoriesClassResponse = CategoriesClassResponse(
            data = emptyList(),
            message = "success",
            success = true
        )
        runTest {
            coEvery { courseDataSource.getCategoriesClass() } returns fakeCategoriesClassResponse
            courseRepo.getCategoriesClass().map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { courseDataSource.getCategoriesClass() }
            }
        }
    }

    @Test
    fun `get categories class, result error`() {
        runTest {
            coEvery { courseDataSource.getCategoriesClass() } throws IllegalStateException("Error")
            courseRepo.getCategoriesClass().map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { courseDataSource.getCategoriesClass() }
            }
        }
    }

    @Test
    fun `get course home, result loading`() {
        val mockCourseHome = mockk<CourseResponse>()
        runTest {
            coEvery { courseDataSource.getCourseHome(any()) } returns mockCourseHome
            courseRepo.getCourseHome("Android").map {
                delay(100)
                it
            }.test {
                delay(130)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { courseDataSource.getCourseHome(any()) }
            }
        }
    }

    @Test
    fun `get course home, result success`() {
        val fakeCategoryCourse = Category(
            id = "c1",
            name = "All"
        )
        val fakeCreatedBy = CreatedBy(
            id = "p1",
            name = "Ika"
        )
        val fakeCourseHomeItemResponse = DataCourseAll(
            category = fakeCategoryCourse,
            classCode = "Class A",
            createdAt = "Cilacap",
            createdBy = fakeCreatedBy,
            description = "desc",
            id = "course1",
            isActive = false,
            level = "basic",
            sold = 1,
            targetAudience = listOf("mahasiswa"),
            thumbnail = "url",
            title = "Belajar Android",
            totalDuration = 5.0,
            totalModule = 10,
            totalRating = 5.0,
            typeClass = "Free",
            price = 10000
        )
        val fakeCourseHomeResponse = CourseResponse(
            data = listOf(fakeCourseHomeItemResponse),
            message = "success",
            success = true

        )
        runTest {
            coEvery { courseDataSource.getCourseHome(any()) } returns fakeCourseHomeResponse
            courseRepo.getCourseHome("Android").map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.category?.name, "All")
                coVerify { courseDataSource.getCourseHome(any()) }
            }
        }
    }

    @Test
    fun `get course home, result empty`() {
        val fakeCourseHomeResponse = CourseResponse(
            data = emptyList(),
            message = "success",
            success = true

        )
        runTest {
            coEvery { courseDataSource.getCourseHome(any()) } returns fakeCourseHomeResponse
            courseRepo.getCourseHome("Android").map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { courseDataSource.getCourseHome(any()) }
            }
        }
    }

    @Test
    fun `get course home, result error`() {
        runTest {
            coEvery { courseDataSource.getCourseHome(any()) } throws IllegalStateException("Error")
            courseRepo.getCourseHome("Android").map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { courseDataSource.getCourseHome(any()) }
            }
        }
    }

    @Test
    fun `get course topic, result loading`() {
        val mockCourseHome = mockk<CourseResponse>()
        runTest {
            coEvery { courseDataSource.getCourseTopic(any()) } returns mockCourseHome
            courseRepo.getCourseTopic("Free").map {
                delay(100)
                it
            }.test {
                delay(130)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { courseDataSource.getCourseTopic(any()) }
            }
        }
    }

    @Test
    fun `get course topic, result success`() {
        val fakeCategoryCourse = Category(
            id = "c1",
            name = "All"
        )
        val fakeCreatedBy = CreatedBy(
            id = "p1",
            name = "Ika"
        )
        val fakeCourseHomeItemResponse = DataCourseAll(
            category = fakeCategoryCourse,
            classCode = "Class A",
            createdAt = "Cilacap",
            createdBy = fakeCreatedBy,
            description = "desc",
            id = "course1",
            isActive = false,
            level = "basic",
            sold = 1,
            targetAudience = listOf("mahasiswa"),
            thumbnail = "url",
            title = "Belajar Android",
            totalDuration = 5.0,
            totalModule = 10,
            totalRating = 5.0,
            typeClass = "Free",
            price = 10000
        )
        val fakeCourseHomeResponse = CourseResponse(
            data = listOf(fakeCourseHomeItemResponse),
            message = "success",
            success = true

        )
        runTest {
            coEvery { courseDataSource.getCourseTopic(any()) } returns fakeCourseHomeResponse
            courseRepo.getCourseTopic("Free").map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.typeClass, "Free")
                coVerify { courseDataSource.getCourseTopic(any()) }
            }
        }
    }

    @Test
    fun `get course topic, result empty`() {
        val fakeCourseHomeResponse = CourseResponse(
            data = emptyList(),
            message = "success",
            success = true

        )
        runTest {
            coEvery { courseDataSource.getCourseTopic(any()) } returns fakeCourseHomeResponse
            courseRepo.getCourseTopic("Free").map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { courseDataSource.getCourseTopic(any()) }
            }
        }
    }

    @Test
    fun `get course topic, result error`() {
        runTest {
            coEvery { courseDataSource.getCourseTopic(any()) } throws IllegalStateException("Error")
            courseRepo.getCourseTopic("Android").map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { courseDataSource.getCourseTopic(any()) }
            }
        }
    }
}
