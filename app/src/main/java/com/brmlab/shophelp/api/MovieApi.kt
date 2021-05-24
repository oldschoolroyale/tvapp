package com.brmlab.shophelp.api

import com.brmlab.shophelp.model.PopularModel
import com.brmlab.shophelp.model.PopularPerson
import com.brmlab.shophelp.model.VideoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    fun getPopularMovie(@Query("api_key")apiKey: String): Call<PopularModel>

    @GET("movie/top_rated")
    fun getTopRated(@Query("api_key")apiKey: String) : Call<PopularModel>

    @GET("person/popular")
    fun getPopularPerson(@Query("api_key")apiKey: String): Call<PopularPerson>
}