package com.bb.movieapi.API

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bb.movieapi.Data.*
import com.bb.movieapi.Database.MovieDB
import com.bb.movieapi.Database.MovieData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

class Repository(
    private val service: Service,
    private val context: Context
) {
    val list = MutableLiveData<Response<MainData>>()
    val imageList = MutableLiveData<Images>()
    val description = MutableLiveData<Description>()
    val key = MutableLiveData<MovieKey>()
    var offlineList=ArrayList<MovieData>()

    suspend fun getOfflineData():ArrayList<MovieData>{
        var job:Deferred<ArrayList<MovieData>> = GlobalScope.async {
            try {
                var db=MovieDB.getInstance(context).getDao()
                offlineList = db.getData() as ArrayList<MovieData>
                Log.i("sizeR","-${offlineList.size}")
            }catch (e:Exception){

            }
            offlineList
        }
        return job.await()
    }

    suspend fun getData() {
        if(Helper.isInternetAvailable(context)){
            try {
                var result = service.getMovieList()
                if (result?.body() != null) {
                    list.postValue(Response.Success(result.body()))
                    var db = MovieDB.getInstance(context).getDao()
                    var arr = result?.body()?.results
//                db.delete()
                    arr?.forEach {
                        GlobalScope.launch {
                            var bitmap: Bitmap? = null
                            bitmap = getBitmap(poster_baseUrl + it.poster_path)
                            Log.i("bytearray2", "image : ${bitmap.toString()}")
                            var data = MovieData(
                                id = it.id,
                                original_title = it.original_title,
                                release_date = it.release_date,
                                adult = it.adult,
                                poster = bitmap
                            )
                            try {
                                db.insertSingle(data)
                            }catch (e:java.lang.Exception){}

                        }

                    }
                }
            } catch (e: Exception) {
                list.postValue(Response.ErrMsg(e.message))
            }
        }else{
            list.postValue(Response.ErrMsg("Network Unavailable!"))
        }
    }

    suspend fun getBitmap(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        Log.i("url", url)
        var job:Deferred<Bitmap?> = GlobalScope.async {
            bitmap = null;
            var inputStream:InputStream?=null
            try {
                inputStream = java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (e:Exception) {}
            var stream = ByteArrayOutputStream()
            bitmap?.let { bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, stream) }
            bitmap
        }
        return job.await()
    }

    suspend fun getImages(id: String) {
        var result = service.getImages(id)
        if (result?.body() != null) {
            imageList.postValue(result.body())
        }
    }

    suspend fun getDescription(id: String) {
        var result = service.getDescription(id)
        if (result?.body() != null) {
            description.postValue(result?.body())
        }
    }

    suspend fun getKey(id: String) {
        var result = service.getMovieKey(id)
        if (result?.body() != null) {
            key.postValue(result.body())
        }
    }
}