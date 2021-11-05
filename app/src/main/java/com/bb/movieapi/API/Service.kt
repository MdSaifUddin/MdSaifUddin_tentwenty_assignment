package com.bb.movieapi.API

import com.bb.movieapi.Data.Description
import com.bb.movieapi.Data.FilePath
import com.bb.movieapi.Data.Images
import com.bb.movieapi.Data.MainData
import com.bb.movieapi.Data.MovieKey
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val baseUrl="https://api.themoviedb.org/3/movie/"
const val apiKey="a6446bb450b9574201e727e4a437c07a"
const val poster_baseUrl="https://image.tmdb.org/t/p/original"
const val youtube_baseUrl="https://www.youtube.com/watch?v="

interface Service {
    @GET("${baseUrl}upcoming?api_key=$apiKey")
    suspend fun getMovieList(): Response<MainData>

    @GET("${baseUrl}{movieId}?api_key=$apiKey")
    suspend fun getDescription(@Path("movieId") movieId:String): Response<Description>

    @GET("${baseUrl}{movieId}/videos?api_key=$apiKey")
    suspend fun getMovieKey(@Path("movieId") movieId:String): Response<MovieKey>

    @GET("${baseUrl}{movieId}/images?api_key=$apiKey")
    suspend fun getImages(@Path("movieId") movieId:String): Response<Images>

}
