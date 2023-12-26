package com.kelompoksatuandsatu.preducation.data.network.api.datasource

import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoriesprogress.CategoriesProgressResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.CategoriesClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categorytypeclass.CategoriesTypeClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.CourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.DetailCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseRequest
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.HistoryItemResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.CourseProgressResponse
import com.kelompoksatuandsatu.preducation.data.network.api.service.PreducationService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CourseDataSourceImplTest {

    @MockK
    lateinit var service: PreducationService

    private lateinit var courseDataSource: CourseDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        courseDataSource = CourseDataSourceImpl(service)
    }

    @Test
    fun getCategoriesClass() {
        runTest {
            val mockResponse = mockk<CategoriesClassResponse>(relaxed = true)
            coEvery { service.getCategoriesClass() } returns mockResponse
            val response = courseDataSource.getCategoriesClass()
            coVerify { service.getCategoriesClass() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCourseHome() {
        runTest {
            val mockResponse = mockk<CourseResponse>(relaxed = true)
            coEvery { service.getCourseHome(any()) } returns mockResponse
            val response = courseDataSource.getCourseHome("Android Development")
            coVerify { service.getCourseHome(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCourseTopic() {
        runTest {
            val mockResponse = mockk<CourseResponse>(relaxed = true)
            coEvery { service.getCourseTopic(any()) } returns mockResponse
            val response = courseDataSource.getCourseTopic("Free")
            coVerify { service.getCourseTopic(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCourseById() {
        runTest {
            val mockResponse = mockk<DetailCourseResponse>(relaxed = true)
            coEvery { service.getCourseById(any()) } returns mockResponse
            val response = courseDataSource.getCourseById("1")
            coVerify { service.getCourseById(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCourseUserProgress() {
        runTest {
            val mockResponse = mockk<CourseProgressResponse>(relaxed = true)
            coEvery { service.getCourseUserProgress(any()) } returns mockResponse
            val response = courseDataSource.getCourseUserProgress("Done")
            coVerify { service.getCourseUserProgress(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCategoriesProgress() {
        runTest {
            val mockResponse = mockk<CategoriesProgressResponse>(relaxed = true)
            coEvery { service.getCategoriesProgress() } returns mockResponse
            val response = courseDataSource.getCategoriesProgress()
            coVerify { service.getCategoriesProgress() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCategoriesTypeClass() {
        runTest {
            val mockResponse = mockk<CategoriesTypeClassResponse>(relaxed = true)
            coEvery { service.getCategoriesTypeClass() } returns mockResponse
            val response = courseDataSource.getCategoriesTypeClass()
            coVerify { service.getCategoriesTypeClass() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun postIndexCourseById() {
        runTest {
            val mockResponse = mockk<ProgressCourseResponse>(relaxed = true)
            val mockRequest = mockk<ProgressCourseRequest>(relaxed = true)
            coEvery { service.postIndexCourseById(any(), any()) } returns mockResponse
            val response = courseDataSource.postIndexCourseById("1", mockRequest)
            coVerify { service.postIndexCourseById(any(), any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun paymentCourse() {
        runTest {
            val mockResponse = mockk<PaymentCourseResponse>(relaxed = true)
            val mockRequest = mockk<PaymentCourseRequest>(relaxed = true)
            coEvery { service.paymentCourse(any()) } returns mockResponse
            val response = courseDataSource.paymentCourse(mockRequest)
            coVerify { service.paymentCourse(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getHistoryPayment() {
        runTest {
            val mockResponse = mockk<HistoryItemResponse>(relaxed = true)
            coEvery { service.getHistoryPayment() } returns mockResponse
            val response = courseDataSource.getHistoryPayment()
            coVerify { service.getHistoryPayment() }
            assertEquals(response, mockResponse)
        }
    }
}
