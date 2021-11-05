package com.bb.movieapi.Database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieData(
    @PrimaryKey
    val id:Integer,
    val original_title: String,
    val release_date: String,
    val poster: Bitmap?=null,
    val adult:Boolean
)