package com.brmlab.shophelp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PopularModel(
        val page: Int,
        @SerializedName("results")val results: List<PopularResult>,
        val total_pages: Int,
        val total_results: Int
)

@Parcelize
data class PopularResult(
        val adult: Boolean,
        val backdrop_path: String,
        val genre_ids: List<Int>,
        val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
): Parcelable