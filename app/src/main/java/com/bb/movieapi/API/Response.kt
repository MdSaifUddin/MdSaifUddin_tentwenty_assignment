package com.bb.movieapi.API

sealed class Response<T>(val data:T?=null,val errMsg:String?=null){
    class Loading<T> : Response<T>()
    class Success<T>(data:T?=null) : Response<T>(data=data)
    class ErrMsg<T>(errMsg: String?=null) : Response<T>(errMsg=errMsg)
}