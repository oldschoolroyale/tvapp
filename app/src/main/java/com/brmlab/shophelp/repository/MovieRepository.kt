package com.brmlab.shophelp.repository

import com.brmlab.shophelp.App
import com.brmlab.shophelp.api.MovieApi
import com.brmlab.shophelp.model.PopularModel
import com.brmlab.shophelp.model.PopularPerson
import com.brmlab.shophelp.model.VideoModel
import com.brmlab.shophelp.utils.BaseResponse
import java.lang.Exception
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApi) {

  suspend fun getMovie(): BaseResponse<PopularModel> {
        try {
            val response = api.getPopularMovie(App.API_KEY).execute()
            return BaseResponse(
                response.code(),
                response.body(),
                response.errorBody()?.string()
            )
        }
        catch (e : Exception){
            return BaseResponse(
                500,
                null,
                e.toString()
            )
        }
    }

    suspend fun getTopRated(): BaseResponse<PopularModel> {
        try {
            val response = api.getTopRated(App.API_KEY).execute()
            return BaseResponse(
                response.code(),
                response.body(),
                response.errorBody()?.string()
            )
        }
        catch (e: Exception){
            return BaseResponse(
                500,
                null,
                e.toString()
            )
        }
    }

    suspend fun getPerson(): BaseResponse<PopularPerson> {
        try {
            val response = api.getPopularPerson(App.API_KEY).execute()
            return BaseResponse(
                response.code(),
                response.body(),
                response.errorBody()?.string()
            )
        }
        catch (e: Exception){
            return BaseResponse(
                500,
                null,
                e.toString()
            )
        }
    }

}