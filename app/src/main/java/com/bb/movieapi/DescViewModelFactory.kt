package com.bb.movieapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bb.movieapi.API.Repository

class DescViewModelFactory(val repository: Repository,val id:String):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DescViewModel(repository,id) as T
    }
}