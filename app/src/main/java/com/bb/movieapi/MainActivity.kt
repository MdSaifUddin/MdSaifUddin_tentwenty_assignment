package com.bb.movieapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bb.movieapi.API.Helper
import com.bb.movieapi.API.Repository
import com.bb.movieapi.API.Service
import com.bb.movieapi.Adapter.DiffAdapter
import com.bb.movieapi.Adapter.DiffUtilOffline
import com.bb.movieapi.Data.MainData
import com.bb.movieapi.Data.MovieItem
import com.bb.movieapi.Database.MovieDB
import com.bb.movieapi.Database.MovieData
import com.bb.movieapi.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var dataBinding:ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        dataBinding.recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)
        var service=Helper.getInstance().create(Service::class.java)
        var repository=Repository(service,applicationContext)

        viewModel=ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
        viewModel.list.observe(this,{
            when(it){
                is com.bb.movieapi.API.Response.Success->{
                    var list:ArrayList<MovieItem> =it.data?.results as ArrayList<MovieItem>
                    var adapter= DiffAdapter()
                    adapter.submitList(list)
                    dataBinding.recyclerView.setHasFixedSize(true)
                    dataBinding.recyclerView.adapter=adapter
                }

                is com.bb.movieapi.API.Response.ErrMsg->{
                    GlobalScope.launch {
//                        delay(3000L)

                        var list: ArrayList<MovieData> = repository.getOfflineData()
                        withContext(Dispatchers.Main){
                            var adapter= DiffUtilOffline()
                            adapter.submitList(list)
                            dataBinding.recyclerView.setHasFixedSize(true)
                            dataBinding.recyclerView.adapter=adapter
//                            Toast.makeText(applicationContext, "Network Error1! ${list.size}", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

                is com.bb.movieapi.API.Response.Loading->{
                    Toast.makeText(applicationContext, "Loading!", Toast.LENGTH_SHORT).show()
                }

            }

        })


    }
}