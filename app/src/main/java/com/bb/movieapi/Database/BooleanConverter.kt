package com.bb.movieapi.Database

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.sql.Blob

class BooleanConverter {

    @TypeConverter
    fun fromBooleanToInt(boolean:Boolean): Int {
        return if(boolean) 1 else 0
    }

    @TypeConverter
    fun fromIntToBoolean(int:Int): Boolean {
        return if(int==1) true else false
    }
}