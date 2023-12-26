package com.kelompoksatuandsatu.preducation.data.repository

import com.kelompoksatuandsatu.preducation.data.network.api.datasource.CourseDataSource
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
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
    fun `get categories class, result success`(){

    }
}