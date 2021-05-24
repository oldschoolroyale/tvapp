package com.brmlab.shophelp.ui.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brmlab.shophelp.model.PopularModel
import com.brmlab.shophelp.model.PopularPerson
import com.brmlab.shophelp.model.VideoModel
import com.brmlab.shophelp.utils.BaseModel
import com.brmlab.shophelp.repository.MovieRepository
import com.brmlab.shophelp.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieViewModel @ViewModelInject constructor(private val repository: MovieRepository) : ViewModel()  {
    private val _slide_list = MutableLiveData<BaseModel<PopularModel>>()
    val slide_list : LiveData<BaseModel<PopularModel>> = _slide_list

    private val _person_list = MutableLiveData<BaseModel<PopularPerson>>()
    val person_list : LiveData<BaseModel<PopularPerson>> = _person_list

    private val _top_rated = MutableLiveData<BaseModel<PopularModel>>()
    val top_rated : LiveData<BaseModel<PopularModel>> = _top_rated

    fun getMovies() {
        viewModelScope.launch {
            _slide_list.value = BaseModel(
                Status.LOADING,
                null
            )

            val resp = CoroutineScope(Dispatchers.IO).async {
                repository.getMovie()
            }.await()
            if (resp.status == 200) {
                _slide_list.value = BaseModel(
                    Status.SUCCESS,
                    resp
                )
            } else {
                _slide_list.value = BaseModel(
                    Status.ERROR,
                    resp
                )
            }
        }
    }

    fun getTopRated(){
        viewModelScope.launch {
            _top_rated.value = BaseModel(
                Status.LOADING,
                null
            )
            val resp = CoroutineScope(Dispatchers.IO).async {
                repository.getTopRated()
            }.await()
            if (resp.status == 200){
                _top_rated.value = BaseModel(
                    Status.SUCCESS,
                    resp
                )
            }
            else{
                _top_rated.value = BaseModel(
                    Status.ERROR,
                    resp
                )
            }
        }
    }

    fun getPerson(){
            viewModelScope.launch {
                _person_list.value = BaseModel(
                    Status.LOADING,
                    null
                )
                val resp = CoroutineScope(Dispatchers.IO).async {
                    repository.getPerson()
                }.await()
                if (resp.status == 200){
                    _person_list.value = BaseModel(
                        Status.SUCCESS,
                        resp
                    )
                }
                else{
                    _person_list.value = BaseModel(
                        Status.ERROR,
                        resp
                    )
                }
            }
        }



    }
