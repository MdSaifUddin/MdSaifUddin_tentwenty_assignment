package com.bb.movieapi.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bb.movieapi.*
import com.bb.movieapi.API.poster_baseUrl
import com.bb.movieapi.Data.MovieItem
import com.bumptech.glide.Glide

class ListAdapter(val list:ArrayList<MovieItem>, val context: Context): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view=LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var data: MovieItem =list.get(position)
        holder.title.text=data.original_title
        holder.releaseDate.text=data.release_date
        if(data.adult)
            holder.adult.text="Only for Adult"
        else
            holder.adult.text="Non Adult"
        Glide.with(context)
            .load(poster_baseUrl+data.poster_path)
            .into(holder.poster)

        holder.parent.setOnClickListener {
            var intent= Intent(context,Description::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("id","${data.id}")
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(viewItem: View):RecyclerView.ViewHolder(viewItem){
        val title=viewItem.findViewById<TextView>(R.id.title)
        val releaseDate=viewItem.findViewById<TextView>(R.id.releaseDate)
        val adult=viewItem.findViewById<TextView>(R.id.adult)
        val poster=viewItem.findViewById<ImageView>(R.id.poster)
        val parent=viewItem.findViewById<ConstraintLayout>(R.id.constraint1)
    }
}

