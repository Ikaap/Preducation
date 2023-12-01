package com.kelompoksatuandsatu.preducation.presentation.feature.profile

data class PopularCourseItem(
    val category: String,
    val rating: Double,
    val title: String,
    val moduleCount: Int,
    val duration: String,
    val level: String,
    val status: String,
    val imageUrl: String
)

object MockData {

    fun getMockPaidTransactions(): List<PopularCourseItem> {
        return listOf(
            PopularCourseItem(
                imageUrl = "https://example.com/course1.jpg",
                category = "Data Science",
                rating = 4.8,
                title = "Introduction to Data Science",
                moduleCount = 12,
                duration = "30 Hours",
                level = "Intermediate",
                status = "Waiting Payment"
            ),
            PopularCourseItem(
                imageUrl = "https://example.com/course3.jpg",
                category = "Mobile App Development",
                rating = 4.5,
                title = "Android App Development",
                moduleCount = 15,
                duration = "40 Hours",
                level = "Intermediate",
                status = "Waiting Payment"
            ),
            PopularCourseItem(
                imageUrl = "https://example.com/course5.jpg",
                category = "Machine Learning",
                rating = 4.9,
                title = "Mastering Machine Learning Algorithms",
                moduleCount = 18,
                duration = "35 Hours",
                level = "Advanced",
                status = "Waiting Payment"
            )
            // Add more items as needed
        )
    }

    fun getMockWaitingTransactions(): List<PopularCourseItem> {
        return listOf(
            PopularCourseItem(
                imageUrl = "https://example.com/course2.jpg",
                category = "Web Development",
                rating = 4.5,
                title = "Full Stack Web Development",
                moduleCount = 20,
                duration = "50 Hours",
                level = "Advanced",
                status = "Paid"
            ),
            PopularCourseItem(
                imageUrl = "https://example.com/course4.jpg",
                category = "Graphic Design",
                rating = 4.7,
                title = "Graphic Design Fundamentals",
                moduleCount = 10,
                duration = "25 Hours",
                level = "Beginner",
                status = "Paid"
            ),
            PopularCourseItem(
                imageUrl = "https://example.com/course6.jpg",
                category = "Cybersecurity",
                rating = 4.6,
                title = "Cybersecurity Essentials",
                moduleCount = 15,
                duration = "30 Hours",
                level = "Intermediate",
                status = "Paid"
            )
            // Add more items as needed
        )
    }
}
