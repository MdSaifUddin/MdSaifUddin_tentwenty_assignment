package com.bb.movieapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bb.movieapi.API.Repository

class MainViewModelFactory(val repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}