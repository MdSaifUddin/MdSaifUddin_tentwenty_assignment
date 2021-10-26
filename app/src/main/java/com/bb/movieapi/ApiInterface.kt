package com.bb.movieapi

import com.bb.movieapi.Data.Description
import com.bb.movieapi.Data.FilePath
import com.bb.movieapi.Data.Images
import com.bb.movieapi.Data.MainData
import com.bb.movieapi.Data.MovieKey
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val baseUrl="https://api.themoviedb.org/3/movie/"
const val apiKey="a6446bb450b9574201e727e4a437c07a"
const val poster_baseUrl="https://image.tmdb.org/t/p/original"
const val youtube_baseUrl="https://www.youtube.com/watch?v="

interface ApiInterface {
    @GET("${baseUrl}upcoming?api_key=$apiKey")
    fun getMovieList(): Call<MainData>

    @GET("${baseUrl}{movieId}?api_key=$apiKey")
    fun getDescription(@Path("movieId") movieId:String): Call<Description>

    @GET("${baseUrl}{movieId}/videos?api_key=$apiKey")
    fun getMovieKey(@Path("movieId") movieId:String): Call<MovieKey>

    @GET("${baseUrl}{movieId}/images?api_key=$apiKey")
    fun getImages(@Path("movieId") movieId:String): Call<Images>

}
object ApiService{
    val apiInterface:ApiInterface
    init {
        val retrofit=Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiInterface=retrofit.create(ApiInterface::class.java)
    }
}