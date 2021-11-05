package com.bb.movieapi.Database
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.sql.Blob

class DrawableConverter {

    @TypeConverter
    fun toBlob(bitmap:Bitmap?): ByteArray? {
        if(bitmap!=null){
            var stream = ByteArrayOutputStream()
            bitmap.compress( Bitmap.CompressFormat.JPEG, 80, stream)
            var bitmapData = stream.toByteArray()
            return bitmapData
        }else{
            return null
        }

    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        if(byteArray!=null){
            var bitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
            return bitmap
        }else{
            return null
        }

    }
}