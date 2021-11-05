package com.bb.movieapi.Data

import androidx.room.Entity

data class MovieItem(
    val poster_path: String,
    val original_title: String,
    val release_date: String,
    val adult:Boolean,
    val id:Integer
)