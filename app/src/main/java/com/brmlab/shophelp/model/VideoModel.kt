package com.brmlab.shophelp.model

data class VideoModel(
    val id: Int,
    val results: List<VideoResults>? = null
)
data class VideoResults(
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)