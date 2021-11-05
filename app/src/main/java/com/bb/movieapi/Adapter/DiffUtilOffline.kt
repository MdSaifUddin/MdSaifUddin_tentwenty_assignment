package com.bb.movieapi.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bb.movieapi.API.Helper
import com.bb.movieapi.Database.MovieData
import com.bb.movieapi.Description
import com.bb.movieapi.R
import com.bumptech.glide.Glide

class DiffUtilOffline: ListAdapter<MovieData, DiffUtilOffline.MyViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var data: MovieData =getItem(position)
        holder.title.text=data.original_title
        holder.releaseDate.text=data.release_date
        if(data.adult)
            holder.adult.text="Only for Adult"
        else
            holder.adult.text="Non Adult"
        holder.poster.setImageBitmap(data.poster)
        holder.parent.setOnClickListener {
            if(Helper.isInternetAvailable(holder.parent.context)){
                var intent= Intent(holder.parent.context, Description::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("id","${data.id}")
                holder.parent.context.startActivity(intent)
            }else{
                Toast.makeText(holder.parent.context, "You are offline!", Toast.LENGTH_SHORT).show()
            }

        }
    }
    class MyViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem){
        val title=viewItem.findViewById<TextView>(R.id.title)
        val releaseDate=viewItem.findViewById<TextView>(R.id.releaseDate)
        val adult=viewItem.findViewById<TextView>(R.id.adult)
        val poster=viewItem.findViewById<ImageView>(R.id.poster)
        val parent=viewItem.findViewById<ConstraintLayout>(R.id.constraint1)
    }

    class DiffUtil: androidx.recyclerview.widget.DiffUtil.ItemCallback<MovieData>() {
        override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem==newItem
        }
    }
}