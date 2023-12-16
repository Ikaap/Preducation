package com.kelompoksatuandsatu.preducation.data.network.api.model.payment.history

import com.google.gson.annotations.SerializedName

data class HistoryPaymentResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Payment>
)

data class Payment(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("courseId")
    val courseId: Course,
    @SerializedName("userId")
    val userId: User,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalPrice")
    val totalPrice: Int,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("createdAt")
    val createdAt: String
)

data class Course(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("classCode")
    val classCode: String,
    @SerializedName("category")
    val category: Category,
    @SerializedName("typeClass")
    val typeClass: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("targetAudience")
    val targetAudience: List<String>,
    @SerializedName("totalModule")
    val totalModule: Int,
    @SerializedName("totalRating")
    val totalRating: Double,
    @SerializedName("totalDuration")
    val totalDuration: Int,
    @SerializedName("sold")
    val sold: Int,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("updatedBy")
    val updatedBy: String
)

data class Category(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("name")
    val name: String
)

data class User(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("image_profile")
    val image_profile: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("isVerify")
    val isVerify: Boolean,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("createdAt")
    val createdAt: String
)
