package com.brmlab.shophelp.api

import com.brmlab.shophelp.model.MovieCredModel
import com.brmlab.shophelp.model.VideoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DescriptionApi {

    @GET("movie/{movie_id}/videos")
    fun getVideo(@Path("movie_id") movieId: Int,
                 @Query("api_key") apiKey: String): Call<VideoModel>

    @GET("movie/{movie_id}/credits")
    fun getMovieCred(@Path("movie_id") movieId: Int,
                 @Query("api_key") apiKey: String): Call<MovieCredModel>
}