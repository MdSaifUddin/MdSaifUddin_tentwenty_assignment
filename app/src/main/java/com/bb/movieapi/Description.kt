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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bb.movieapi.API.Helper
import com.bb.movieapi.API.Repository
import com.bb.movieapi.API.Service
import com.bb.movieapi.API.poster_baseUrl
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
    lateinit var viewModel:DescViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_description)

        id=intent.extras!!.getString("id").toString()
        imageSlider=findViewById(R.id.imageSlider)


        var service=Helper.getInstance().create(Service::class.java)
        var repository=Repository(service,applicationContext)
        viewModel=ViewModelProvider(this,DescViewModelFactory(repository,id)).get(DescViewModel::class.java)

        viewModel.imageList.observe(this,{
            var pathList:List<FilePath> = ArrayList()
            pathList= it.backdrops
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
        })

        viewModel.desc.observe(this,{
                    findViewById<TextView>(R.id.title).text=it.original_title
                    findViewById<TextView>(R.id.date).text=it.release_date
                    findViewById<TextView>(R.id.overview).text=it.overview
                    var genres:String=""
                    it.genres?.forEach {
                        genres+=it.name+", "
                    }
                    genres= genres.substring(0,genres.length-2)
                    findViewById<TextView>(R.id.genres).text=genres
        })

        viewModel.key.observe(this,{
            var keyList=it.results
                    var key=keyList?.get(0)!!.key

                    findViewById<Button>(R.id.watchBtn).setOnClickListener {
                        var intent= Intent(this@Description,VideoPlayer::class.java)
                        intent.putExtra("id",key)
                        startActivity(intent)
                        Log.i("MovieKey", key.toString())
                    }
        })
    }

}