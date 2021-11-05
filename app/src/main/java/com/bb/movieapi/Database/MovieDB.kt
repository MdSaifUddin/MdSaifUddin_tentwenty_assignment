package com.bb.movieapi.Database

import android.content.Context
import androidx.room.*

@Database(entities = [MovieData::class],version=1)
@TypeConverters(BooleanConverter::class,DrawableConverter::class)
abstract class MovieDB:RoomDatabase() {
    abstract fun getDao(): DAO

    companion object{

        @Volatile
        var INSTANCE:MovieDB?=null

        fun getInstance(context:Context):MovieDB{
            if(INSTANCE==null){
                synchronized(context){
                    INSTANCE= Room.databaseBuilder(
                        context,
                        MovieDB::class.java,
                        "movie"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }
}