package com.brmlab.shophelp.ui.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brmlab.shophelp.model.MovieCredModel
import com.brmlab.shophelp.model.VideoModel
import com.brmlab.shophelp.repository.DescriptionRepository
import com.brmlab.shophelp.utils.BaseModel
import com.brmlab.shophelp.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DescriptionViewModel @ViewModelInject constructor(private val
                                                        descriptionRepository: DescriptionRepository)
    :ViewModel(){

    private val _video_list = MutableLiveData<BaseModel<VideoModel>>()
    val video_list : LiveData<BaseModel<VideoModel>> = _video_list

    private val _movie_cred = MutableLiveData<BaseModel<MovieCredModel>>()
    val movie_cred : LiveData<BaseModel<MovieCredModel>> = _movie_cred


    fun getVideo(movieId: Int){
        viewModelScope.launch {
            _video_list.value = BaseModel(
                Status.LOADING,
                null
            )
            val resp = CoroutineScope(Dispatchers.IO).async {
                descriptionRepository.getVideo(movieId)
            }.await()
            if (resp.status == 200){
                _video_list.value = BaseModel(
                    Status.SUCCESS,
                    resp
                )
            }
            else{
                _video_list.value = BaseModel(
                    Status.ERROR,
                    resp
                )
            }
        }
    }

    fun getMovieCred(movieId: Int){
        viewModelScope.launch {
            _movie_cred.value = BaseModel(
                Status.LOADING,
                null
            )
            val resp = CoroutineScope(Dispatchers.IO).async {
                descriptionRepository.getMovieDesc(movieId)
            }.await()
            if (resp.status == 200){
                _movie_cred.value = BaseModel(
                    Status.SUCCESS,
                    resp
                )
            }
            else{
                _movie_cred.value = BaseModel(
                    Status.ERROR,
                    resp
                )
            }
        }
    }
}