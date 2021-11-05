package com.bb.movieapi.Database

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DAO{
    @Insert
    suspend fun insertSingle(movieData: MovieData)

    @Insert
    suspend fun insertList(list: List<MovieData>)

    @Query("delete from movie")
    suspend fun delete()

    @Query("select * from movie")
    suspend fun getData():List<MovieData>
}