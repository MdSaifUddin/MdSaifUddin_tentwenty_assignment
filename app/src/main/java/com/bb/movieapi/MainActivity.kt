package com.bb.movieapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bb.movieapi.Data.MainData
import com.bb.movieapi.Data.MovieItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)

        val movies: Call<MainData> = ApiService.apiInterface.getMovieList()
        movies.enqueue(object:Callback<MainData>{
            override fun onResponse(call: Call<MainData>, response: Response<MainData>) {
                var list:List<MovieItem> = ArrayList()
                list= response.body()?.results!!
                Log.i("result","list-${list.toString()}")
                var adapter=com.bb.movieapi.Adapter.ListAdapter(list,applicationContext)
                recyclerView.adapter=adapter
            }

            override fun onFailure(call: Call<MainData>, t: Throwable) {
                Log.i("Status","Failed")
            }
        })
    }
}