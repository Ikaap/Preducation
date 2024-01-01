
package com.kelompoksatuandsatu.preducation.data.repository

import app.cash.turbine.test
import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoriesprogress.CategoriesProgressItemResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoriesprogress.CategoriesProgressResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.CategoriesClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categoryclass.Data
import com.kelompoksatuandsatu.preducation.data.network.api.model.category.categorytypeclass.CategoriesTypeClassResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.Category
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.CourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.CreatedBy
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.courseall.DataCourseAll
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.DetailCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.DataX
import com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.progress.ProgressCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.PaymentCourseResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.HistoryItemResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.CourseId
import com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.CourseProgressItemResponse
import com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.CourseProgressResponse
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.DetailCourseViewParam
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.VideoViewParam
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
    lateinit var dataSource: CourseDataSource

    private lateinit var courseRepo: CourseRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        courseRepo = CourseRepositoryImpl(dataSource)
    }

    @Test
    fun `get categories class, result loading`() {
        val mockCategoriesResponse = mockk<CategoriesClassResponse>(relaxed = true)
        runTest {
            coEvery { dataSource.getCategoriesClass() } returns mockCategoriesResponse
            courseRepo.getCategoriesClass().map {
                delay(100)
                it
            }.test {
                delay(3130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
            }
        }
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
            coEvery { dataSource.getCategoriesClass() } returns fakeCategoriesClassResponse
            courseRepo.getCategoriesClass().map {
                delay(100)
                it
            }.test {
                delay(3320)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.name, "All")
                coVerify { dataSource.getCategoriesClass() }
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
            coEvery { dataSource.getCategoriesClass() } returns fakeCategoriesClassResponse
            courseRepo.getCategoriesClass().map {
                delay(100)
                it
            }.test {
                delay(3320)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCategoriesClass() }
            }
        }
    }

    @Test
    fun `get categories class, result error`() {
        runTest {
            coEvery { dataSource.getCategoriesClass() } throws IllegalStateException("Error")
            courseRepo.getCategoriesClass().map {
                delay(100)
                it
            }.test {
                delay(3320)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCategoriesClass() }
            }
        }
    }

    @Test
    fun `get course home, result loading`() {
        val mockCourseHome = mockk<CourseResponse>()
        runTest {
            coEvery { dataSource.getCourseHome(any(), any(), any()) } returns mockCourseHome
            courseRepo.getCourseHome("Android", "Free", "Title").map {
                delay(100)
                it
            }.test {
                delay(3130)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
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
            title = "Basics of Android Development",
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
            coEvery { dataSource.getCourseHome(any(), any(), any()) } returns fakeCourseHomeResponse
            courseRepo.getCourseHome("Android", "Free", "Title").map {
                delay(100)
                it
            }.test {
                delay(3330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.title, "Basics of Android Development")
                coVerify { dataSource.getCourseHome(any(), any(), any()) }
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
            coEvery { dataSource.getCourseHome(any(), any(), any()) } returns fakeCourseHomeResponse
            courseRepo.getCourseHome("Android", "Free", "Title").map {
                delay(100)
                it
            }.test {
                delay(3330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCourseHome(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `get course home, result error`() {
        runTest {
            coEvery { dataSource.getCourseHome(any(), any(), any()) } throws IllegalStateException("Error")
            courseRepo.getCourseHome("Android", "Free", "Title").map {
                delay(100)
                it
            }.test {
                delay(3330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCourseHome(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `get course home filter, result loading`() {
        val mockCourseHome = mockk<CourseResponse>()
        runTest {
            coEvery { dataSource.getCourseHomeFilter(any(), any(), any()) } returns mockCourseHome
            courseRepo.getCourseHomeFilter(listOf("Android", "Web"), "Free", "Title").map {
                delay(100)
                it
            }.test {
                delay(3130)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
            }
        }
    }

    @Test
    fun `get course home filter, result success`() {
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
            title = "Basics of Android Development",
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
            coEvery { dataSource.getCourseHomeFilter(any(), any(), any()) } returns fakeCourseHomeResponse
            courseRepo.getCourseHomeFilter(listOf("Android", "Web"), "Free", "Title").map {
                delay(100)
                it
            }.test {
                delay(3330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.title, "Basics of Android Development")
                coVerify { dataSource.getCourseHomeFilter(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `get course home filter, result empty`() {
        val fakeCourseHomeResponse = CourseResponse(
            data = emptyList(),
            message = "success",
            success = true

        )
        runTest {
            coEvery { dataSource.getCourseHomeFilter(any(), any(), any()) } returns fakeCourseHomeResponse
            courseRepo.getCourseHomeFilter(listOf("Android", "Web"), "Free", "Title").map {
                delay(100)
                it
            }.test {
                delay(3330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCourseHomeFilter(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `get course home filter, result error`() {
        runTest {
            coEvery { dataSource.getCourseHomeFilter(any(), any(), any()) } throws IllegalStateException("Error")
            courseRepo.getCourseHomeFilter(listOf("Android", "Web"), "Free", "Title").map {
                delay(100)
                it
            }.test {
                delay(3330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCourseHomeFilter(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `post index course by id`() {
        val mockProgressCourseData = DataX(
            courseId = "cc1",
            id = "id1",
            createdAt = "cilacap",
            isActive = true,
            percentage = 90,
            status = "status",
            indexProgress = 7,
            userId = "us1",
            v = 1
        )
        val mockProgressCourseResponse = ProgressCourseResponse(
            data = mockProgressCourseData,
            message = "success",
            success = true
        )
        val mockVideoViewParam = VideoViewParam(
            id = "v1",
            title = "video 1",
            duration = 5,
            index = 1,
            isWatch = true,
            nextVideo = false,
            videoUrl = "url"
        )
        runTest {
            coEvery {
                dataSource.postIndexCourseById(
                    any(),
                    any()
                )
            } returns mockProgressCourseResponse
            courseRepo.postIndexCourseById("course1", mockVideoViewParam).map {
                delay(100)
                it
            }.test {
                delay(230)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.postIndexCourseById(any(), any()) }
            }
        }
    }

    @Test
    fun `get categories progress, result loading`() {
        val mockCategoriesProgressResponse = mockk<CategoriesProgressResponse>(relaxed = true)
        runTest {
            coEvery { dataSource.getCategoriesProgress() } returns mockCategoriesProgressResponse
            courseRepo.getCategoriesProgress().map {
                delay(100)
                it
            }.test {
                delay(2130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
            }
        }
    }

    @Test
    fun `get categories progress, result success`() {
        val fakeCategoryProgressItemResponse = CategoriesProgressItemResponse(
            name = "Done"
        )
        val fakeCategoriesProgressResponse = CategoriesProgressResponse(
            data = listOf(fakeCategoryProgressItemResponse),
            message = "success",
            success = true
        )
        runTest {
            coEvery { dataSource.getCategoriesProgress() } returns fakeCategoriesProgressResponse
            courseRepo.getCategoriesProgress().map {
                delay(100)
                it
            }.test {
                delay(2320)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.nameCategory, "Done")
                coVerify { dataSource.getCategoriesProgress() }
            }
        }
    }

    @Test
    fun `get categories progress, result empty`() {
        val fakeCategoriesProgressResponse = CategoriesProgressResponse(
            data = emptyList(),
            message = "success",
            success = true
        )
        runTest {
            coEvery { dataSource.getCategoriesProgress() } returns fakeCategoriesProgressResponse
            courseRepo.getCategoriesProgress().map {
                delay(100)
                it
            }.test {
                delay(2320)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCategoriesProgress() }
            }
        }
    }

    @Test
    fun `get categories progress, result error`() {
        runTest {
            coEvery { dataSource.getCategoriesProgress() } throws IllegalStateException("Error")
            courseRepo.getCategoriesProgress().map {
                delay(100)
                it
            }.test {
                delay(2320)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCategoriesProgress() }
            }
        }
    }

    @Test
    fun `get categories type class, result loading`() {
        val mockCategoriesTypeClassResponse = mockk<CategoriesTypeClassResponse>(relaxed = true)
        runTest {
            coEvery { dataSource.getCategoriesTypeClass() } returns mockCategoriesTypeClassResponse
            courseRepo.getCategoriesTypeClass().map {
                delay(100)
                it
            }.test {
                delay(2130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
            }
        }
    }

    @Test
    fun `get categories type class, result success`() {
        val fakeCategoryTypeClassItemResponse =
            com.kelompoksatuandsatu.preducation.data.network.api.model.category.categorytypeclass.Data(
                name = "Premium"
            )
        val fakeCategoriesTypeClassResponse = CategoriesTypeClassResponse(
            data = listOf(fakeCategoryTypeClassItemResponse),
            message = "success",
            success = true
        )
        runTest {
            coEvery { dataSource.getCategoriesTypeClass() } returns fakeCategoriesTypeClassResponse
            courseRepo.getCategoriesTypeClass().map {
                delay(100)
                it
            }.test {
                delay(2320)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.nameCategory, "Premium")
                coVerify { dataSource.getCategoriesTypeClass() }
            }
        }
    }

    @Test
    fun `get categories type class, result empty`() {
        val fakeCategoriesTypeClassResponse = CategoriesTypeClassResponse(
            data = emptyList(),
            message = "success",
            success = true
        )
        runTest {
            coEvery { dataSource.getCategoriesTypeClass() } returns fakeCategoriesTypeClassResponse
            courseRepo.getCategoriesTypeClass().map {
                delay(100)
                it
            }.test {
                delay(2320)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCategoriesTypeClass() }
            }
        }
    }

    @Test
    fun `get categories type class, result error`() {
        runTest {
            coEvery { dataSource.getCategoriesTypeClass() } throws IllegalStateException("Error")
            courseRepo.getCategoriesTypeClass().map {
                delay(100)
                it
            }.test {
                delay(2320)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCategoriesTypeClass() }
            }
        }
    }

    @Test
    fun `get course user progress, result loading`() {
        val mockCourseProgress = mockk<CourseProgressResponse>()
        runTest {
            coEvery { dataSource.getCourseUserProgress(any()) } returns mockCourseProgress
            courseRepo.getCourseUserProgress("Done").map {
                delay(100)
                it
            }.test {
                delay(3130)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
            }
        }
    }

    @Test
    fun `get course user progress, result success`() {
        val fakeCategory =
            com.kelompoksatuandsatu.preducation.data.network.api.model.progress.courseprogress.Category(
                id = "c1",
                name = "Progress",
                imageCategory = "url"
            )
        val fakeCourseId = CourseId(
            classCode = "Class A",
            createdAt = "Cilacap",
            description = "desc",
            id = "course1",
            isActive = false,
            level = "basic",
            sold = 1,
            targetAudience = listOf("mahasiswa"),
            thumbnail = "url",
            title = "Basics of Android Development",
            totalDuration = 5,
            totalModule = 10,
            totalRating = 5.0,
            typeClass = "Free",
            price = 10000,
            category = fakeCategory
        )
        val fakeCourseProgressItemResponse = CourseProgressItemResponse(
            createdAt = "Cilacap",
            id = "course1",
            isActive = false,
            indexProgress = 3,
            percentage = 80,
            status = "status",
            userId = "us1",
            courseId = fakeCourseId,
            v = 1
        )
        val fakeCourseProgressResponse = CourseProgressResponse(
            data = listOf(fakeCourseProgressItemResponse),
            message = "success",
            success = true

        )
        runTest {
            coEvery { dataSource.getCourseUserProgress(any()) } returns fakeCourseProgressResponse
            courseRepo.getCourseUserProgress("Done").map {
                delay(100)
                it
            }.test {
                delay(3330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.percentage, 80)
                coVerify { dataSource.getCourseUserProgress(any()) }
            }
        }
    }

    @Test
    fun `get course user progress, result empty`() {
        val fakeCourseProgressResponse = CourseProgressResponse(
            data = emptyList(),
            message = "success",
            success = true

        )
        runTest {
            coEvery { dataSource.getCourseUserProgress(any()) } returns fakeCourseProgressResponse
            courseRepo.getCourseUserProgress("Done").map {
                delay(100)
                it
            }.test {
                delay(3330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCourseUserProgress(any()) }
            }
        }
    }

    @Test
    fun `get course user progress, result error`() {
        runTest {
            coEvery { dataSource.getCourseUserProgress(any()) } throws IllegalStateException("Error")
            courseRepo.getCourseUserProgress("Done").map {
                delay(100)
                it
            }.test {
                delay(3330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCourseUserProgress(any()) }
            }
        }
    }

    @Test
    fun `payment course, result loading`() {
        val mockPaymentCourse = mockk<PaymentCourseResponse>()
        val mockDetailCourse = mockk<DetailCourseViewParam>()
        runTest {
            coEvery { dataSource.paymentCourse(any()) } returns mockPaymentCourse
            courseRepo.paymentCourse(mockDetailCourse).map {
                delay(100)
                it
            }.test {
                delay(2130)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
            }
        }
    }

    @Test
    fun `payment course, result success`() {
        val fakePaymentItemResponse =
            com.kelompoksatuandsatu.preducation.data.network.api.model.payment.Data(
                token = "token",
                redirectUrl = "url"
            )
        val fakePaymentResponse = PaymentCourseResponse(
            data = fakePaymentItemResponse,
            message = "success",
            success = true

        )

        val fakePaymentRequest = DetailCourseViewParam(
            category = null,
            chapters = null,
            classCode = "A1",
            createdAt = "Cilacap",
            description = "desc",
            id = "dc01",
            isActive = false,
            level = "level",
            price = 10000,
            sold = 2,
            targetAudience = listOf("mahasiswa"),
            title = "Basic Figma",
            totalDuration = 5,
            totalModule = 10,
            totalRating = 4.5,
            typeClass = "free",
            updatedAt = "Cilacap",
            thumbnail = "img"
        )
        runTest {
            coEvery { dataSource.paymentCourse(any()) } returns fakePaymentResponse
            courseRepo.paymentCourse(fakePaymentRequest).map {
                delay(100)
                it
            }.test {
                delay(2330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertTrue(data.payload?.data?.redirectUrl?.isNotEmpty() == true)
                coVerify { dataSource.paymentCourse(any()) }
            }
        }
    }
    /*
        @Test
        fun `payment course, result empty`() {
            val fakePaymentItemResponse =
                com.kelompoksatuandsatu.preducation.data.network.api.model.payment.Data(
                    token = null,
                    redirectUrl = null
                )
            val fakePaymentResponse = PaymentCourseResponse(
                data = fakePaymentItemResponse,
                message = "success",
                success = true

            )

            val fakePaymentRequest = DetailCourseViewParam(
                category = null,
                chapters = null,
                classCode = "A1",
                createdAt = "Cilacap",
                description = "desc",
                id = "dc01",
                isActive = false,
                level = "level",
                price = 10000,
                sold = 2,
                targetAudience = listOf("mahasiswa"),
                title = "Basic Figma",
                totalDuration = 5,
                totalModule = 10,
                totalRating = 4.5,
                typeClass = "free",
                updatedAt = "Cilacap",
                thumbnail = "img"
            )
            runTest {
                coEvery { dataSource.paymentCourse(any()) } returns fakePaymentResponse
                courseRepo.paymentCourse(fakePaymentRequest).map {
                    delay(100)
                    it
                }.test {
                    delay(2330)
                    val data = expectMostRecentItem()
                    assertTrue(data is ResultWrapper.Empty)
                    coVerify { dataSource.paymentCourse(any()) }
                }
            }
        }
     */

    @Test
    fun `payment course, result error`() {
        val fakePaymentRequest = DetailCourseViewParam(
            category = null,
            chapters = null,
            classCode = "A1",
            createdAt = "Cilacap",
            description = "desc",
            id = "dc01",
            isActive = false,
            level = "level",
            price = 10000,
            sold = 2,
            targetAudience = listOf("mahasiswa"),
            title = "Basic Figma",
            totalDuration = 5,
            totalModule = 10,
            totalRating = 4.5,
            typeClass = "free",
            updatedAt = "Cilacap",
            thumbnail = "img"
        )
        runTest {
            coEvery { dataSource.paymentCourse(any()) } throws IllegalStateException("Error")
            courseRepo.paymentCourse(fakePaymentRequest).map {
                delay(100)
                it
            }.test {
                delay(2330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.paymentCourse(any()) }
            }
        }
    }

    @Test
    fun `get course by id, result loading`() {
        val mockCourseById = mockk<DetailCourseResponse>()
        runTest {
            coEvery { dataSource.getCourseById(any()) } returns mockCourseById
            courseRepo.getCourseById("dc01").map {
                delay(100)
                it
            }.test {
                delay(2130)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
            }
        }
    }

    @Test
    fun `get course by id, result success`() {
        val fakeCourseItemById = com.kelompoksatuandsatu.preducation.data.network.api.model.course.detailcourse.Data(
            category = null,
            chapters = null,
            classCode = "A1",
            createdAt = "Cilacap",
            description = "desc",
            id = "dc01",
            isActive = false,
            level = "level",
            price = 10000,
            sold = 2,
            targetAudience = listOf("mahasiswa"),
            title = "Basic Figma",
            totalDuration = 5,
            totalModule = 10,
            totalRating = 4.5,
            typeClass = "free",
            updatedAt = "Cilacap",
            thumbnail = "img",
            createdBy = null
        )
        val fakeCourseById = DetailCourseResponse(
            data = fakeCourseItemById,
            message = "success",
            success = true

        )
        runTest {
            coEvery { dataSource.getCourseById(any()) } returns fakeCourseById
            courseRepo.getCourseById("dc01").map {
                delay(100)
                it
            }.test {
                delay(2330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.title, "Basic Figma")
                coVerify { dataSource.getCourseById(any()) }
            }
        }
    }

    // Get course by id, result empty

    @Test
    fun `get course by id, result error`() {
        runTest {
            coEvery { dataSource.getCourseById(any()) } throws IllegalStateException("Error")
            courseRepo.getCourseById("dc01").map {
                delay(100)
                it
            }.test {
                delay(2330)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCourseById(any()) }
            }
        }
    }

    @Test
    fun `get history payment, result loading`() {
        val mockHistoryPaymentResponse = mockk<HistoryItemResponse>(relaxed = true)
        runTest {
            coEvery { dataSource.getHistoryPayment() } returns mockHistoryPaymentResponse
            courseRepo.getHistoryPayment().map {
                delay(100)
                it
            }.test {
                delay(130)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { dataSource.getHistoryPayment() }
            }
        }
    }

    @Test
    fun `get  history payment, result success`() {
        val fakeHistoryPaymentData = com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history.Data(
            courseId = null,
            createdAt = "Cilacap",
            id = "p1",
            isActive = false,
            paymentType = "bca",
            status = "status",
            totalPrice = 12000,
            updatedAt = "Cilacap",
            userId = null
        )
        val fakeHistoryPaymentResponse = HistoryItemResponse(
            data = listOf(fakeHistoryPaymentData),
            message = "success",
            success = true
        )
        runTest {
            coEvery { dataSource.getHistoryPayment() } returns fakeHistoryPaymentResponse
            courseRepo.getHistoryPayment().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                assertEquals(data.payload?.get(0)?.id, "p1")
                coVerify { dataSource.getHistoryPayment() }
            }
        }
    }

    @Test
    fun `get history payment, result empty`() {
        val fakeHistoryPaymentResponse = HistoryItemResponse(
            data = emptyList(),
            message = "success",
            success = true
        )
        runTest {
            coEvery { dataSource.getHistoryPayment() } returns fakeHistoryPaymentResponse
            courseRepo.getHistoryPayment().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getHistoryPayment() }
            }
        }
    }

    @Test
    fun `get  history payment, result error`() {
        runTest {
            coEvery { dataSource.getHistoryPayment() } throws IllegalStateException("Error")
            courseRepo.getHistoryPayment().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getHistoryPayment() }
            }
        }
    }
}
