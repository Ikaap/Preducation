package com.kelompoksatuandsatu.preducation.data.local.dummy

import com.kelompoksatuandsatu.preducation.model.course.detailcourse.ChapterViewParam
import com.kelompoksatuandsatu.preducation.model.course.detailcourse.VideoViewParam
import kotlin.random.Random

interface VideoData {
    fun getListVideo(): List<ChapterViewParam>
    fun getUrlVideo(videoTitle: String): String?
    val random: Random
    fun getNext(): String
}

class VideoDataImpl(override val random: Random) : VideoData {
    override fun getListVideo(): List<ChapterViewParam> {
        return listOf(
            ChapterViewParam(
                "ika",
                "c1",
                false,
                "Chapter 1 - Pendahuluan",
                18,
                listOf(
                    VideoViewParam(
                        4,
                        "v1",
                        1,
                        "Our Beloved Summer OST Part.2",
                        "Jv23ekocjT4",
                        false,
                        false
                    ),
                    VideoViewParam(
                        5,
                        "v2",
                        2,
                        "Lee Hi Breath",
                        "5iSlfF8TQ9k",
                        false,
                        false
                    ),
                    VideoViewParam(
                        4,
                        "v3",
                        3,
                        "DO Somebady",
                        "jNYVbmVIP4A",
                        false,
                        false
                    ),
                    VideoViewParam(
                        5,
                        "v4",
                        4,
                        "Beautiful",
                        "xXWyJEo6VgA",
                        false,
                        false
                    )
                )

            )
        )
    }

    override fun getUrlVideo(videoTitle: String): String? {
        val video = getListVideo().flatMap { it.videos ?: emptyList() }
        return video.find { it.title == videoTitle }?.videoUrl
    }

    override fun getNext(): String {
        return getListVideo().get(random.nextInt(getListVideo().size)).toString()
    }
}
