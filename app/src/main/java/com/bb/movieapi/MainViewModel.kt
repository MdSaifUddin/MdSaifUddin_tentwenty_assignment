package com.bb.movieapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bb.movieapi.API.Repository
import com.bb.movieapi.API.Response
import com.bb.movieapi.Data.MainData
import com.bb.movieapi.Database.MovieData
import kotlinx.coroutines.launch

class MainViewModel(val repository: Repository):ViewModel() {
    init {
        viewModelScope.launch {
            repository.getData()
        }
    }

    val offlineList:ArrayList<MovieData>
    get() = repository.offlineList

    val list:LiveData<Response<MainData>>
    get()=repository.list
}