package com.brmlab.shophelp.repository

import com.brmlab.shophelp.App
import com.brmlab.shophelp.api.DescriptionApi
import com.brmlab.shophelp.model.MovieCredModel
import com.brmlab.shophelp.model.VideoModel
import com.brmlab.shophelp.utils.BaseResponse
import java.lang.Exception
import javax.inject.Inject

class DescriptionRepository @Inject constructor(private val descriptionApi: DescriptionApi) {

    suspend fun getVideo(movieId: Int): BaseResponse<VideoModel> {
        try {
            val response = descriptionApi.getVideo(movieId, App.API_KEY).execute()
            return BaseResponse(
                response.code(),
                response.body(),
                response.errorBody()?.string()
            )
        }
        catch (e: Exception){
            return BaseResponse(500, null, e.toString()
            )
        }
    }
    suspend fun getMovieDesc(movieId: Int): BaseResponse<MovieCredModel> {
        try {
            val response = descriptionApi.getMovieCred(movieId, App.API_KEY).execute()
            return BaseResponse(
                response.code(),
                response.body(),
                response.errorBody()?.string()
            )
        }
        catch (e: Exception){
            return BaseResponse(500, null, e.toString()
            )
        }
    }
}