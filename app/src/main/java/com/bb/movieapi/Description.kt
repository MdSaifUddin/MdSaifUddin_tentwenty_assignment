package com.bb.movieapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bb.movieapi.Data.Description
import com.bb.movieapi.Data.FilePath
import com.bb.movieapi.Data.Images
import com.bb.movieapi.Data.MovieKey
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.glide.GlideImageLoaderFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Description : AppCompatActivity() {
    lateinit var id:String
    lateinit var imageSlider:ImageSlider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_description)

        id=intent.extras.getString("id").toString()
        imageSlider=findViewById(R.id.imageSlider)

        lifecycleScope.launch {
            getImages()
            getDescription()
            getMovieKey()
        }
    }

    suspend fun getImages(){
        lifecycleScope.launch {
            var images: Call<Images> =ApiService.apiInterface.getImages(id)
            images.enqueue(object:retrofit2.Callback<Images>{
                override fun onResponse(call: Call<Images>, response: Response<Images>) {
                    var pathList:List<FilePath> = ArrayList()
                    pathList= response.body()?.backdrops!!
                    var imageList=ArrayList<String>()
                    pathList.forEach {
                        imageList.add("${poster_baseUrl}"+it.file_path)
                    }
                    if(imageList.size>5){
                        imageList= imageList.slice(0..4).toList() as ArrayList<String>
                    }
                    imageSlider.adapter= SliderAdapter(this@Description,
                        GlideImageLoaderFactory(),
                        imageUrls = imageList
                    )
                    Log.i("images",":${imageList.toString()}")
                }

                override fun onFailure(call: Call<Images>, t: Throwable) {
                    Log.i("status","Failed")
                }

            })
        }
    }

    suspend fun getDescription(){
        lifecycleScope.launch{
            var description:Call<Description> = ApiService.apiInterface.getDescription(id)
            description.enqueue(object:Callback<Description>{
                override fun onResponse(call: Call<Description>, response: Response<Description>) {
                    Log.i("description","${response.body()?.original_title}")
                    findViewById<TextView>(R.id.title).text=response.body()?.original_title
                    findViewById<TextView>(R.id.date).text=response.body()?.release_date
                    findViewById<TextView>(R.id.overview).text=response.body()?.overview
                    var genres:String=""
                    response.body()?.genres?.forEach {
                        genres+=it.name+", "
                    }
                    genres= genres.substring(0,genres.length-2)
                    findViewById<TextView>(R.id.genres).text=genres
                }

                override fun onFailure(call: Call<Description>, t: Throwable) {
                    Log.i("description","Failed")
                }
            })
        }
    }

    suspend fun getMovieKey(){
        lifecycleScope.launch {
            var movieKey:Call<MovieKey> = ApiService.apiInterface.getMovieKey(id)
            movieKey.enqueue(object:Callback<MovieKey>{
                override fun onResponse(call: Call<MovieKey>, response: Response<MovieKey>) {
                    var keyList=response.body()?.results
                    var key=keyList?.get(0)!!.key

                    findViewById<Button>(R.id.watchBtn).setOnClickListener {
                        var intent= Intent(this@Description,VideoPlayer::class.java)
                        intent.putExtra("id",key)
                        startActivity(intent)
                        Log.i("MovieKey", key.toString())
                    }
                }

                override fun onFailure(call: Call<MovieKey>, t: Throwable) {
                    Log.i("MovieKey","Failed")
                }
            })
        }
    }
    companion object {
        private const val SLIDE_NUMBER = 10
    }
}