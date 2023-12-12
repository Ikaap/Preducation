package com.kelompoksatuandsatu.preducation.data.network.api.model.courseprogress

import com.google.gson.annotations.SerializedName
import com.kelompoksatuandsatu.preducation.model.Course

data class CourseProgressItemResponse(

	@SerializedName("createdAt")
	val createdAt: String?,

	@SerializedName("percentage")
	val percentage: Int?,

	@SerializedName("__v")
	val v: Int?,

	@SerializedName("indexProgress")
	val indexProgress: Int?,

	@SerializedName("_id")
	val id: String?,

	@SerializedName("isActive")
	val isActive: Boolean?,

	@SerializedName("userId")
	val userId: String?,

	@SerializedName("courseId")
	val courseId: CourseId?,

	@SerializedName("status")
	val status: String?
)

data class CourseId(

	@SerializedName("totalDuration")
	val totalDuration: Int?,

	@SerializedName("classCode")
	val classCode: String?,

	@SerializedName("sold")
	val sold: Int?,

	@SerializedName("thumbnail")
	val thumbnail: String?,

	@SerializedName("level")
	val level: String?,

	@SerializedName("description")
	val description: String?,

	@SerializedName("totalRating")
	val totalRating: Int?,

	@SerializedName("title")
	val title: String?,

	@SerializedName("isActive")
	val isActive: Boolean?,

	@SerializedName("totalModule")
	val totalModule: Int?,

	@SerializedName("createdAt")
	val createdAt: String?,

	@SerializedName("targetAudience")
	val targetAudience: List<String?>?,

	@SerializedName("price")
	val price: Int?,

	@SerializedName("typeClass")
	val typeClass: String?,

	@SerializedName("_id")
	val id: String?,

	@SerializedName("category")
	val category: Category?
)

data class Category(

	@SerializedName("name")
	val name: String?,

	@SerializedName("_id")
	val id: String?,

	@SerializedName("imageCategory")
	val imageCategory: String?
)

fun CourseProgressItemResponse.toCourseProgress() = Course(
	titleCourse = this.courseId?.title.orEmpty(),
	nameCategoryPopular = this.courseId?.category?.name.orEmpty(),
	imgUrlPopularCourse = this.courseId?.thumbnail.orEmpty(),
	ratingCourse = (this.courseId?.totalRating ?: 0).toString(),
	levelCourse = this.courseId?.level.orEmpty(),
	moduleCourse = this.courseId?.totalModule.toString(),
	durationCourse = this.courseId?.totalDuration.toString(),
	progress = this.indexProgress ?: 0,
	statusPayment = this.status.orEmpty(),
	type = this.courseId?.typeClass.orEmpty(),
	priceCourse = this.courseId?.price?: 0
)

fun Collection<CourseProgressItemResponse>.toCourseProgressList() = this.map { it.toCourseProgress() }
