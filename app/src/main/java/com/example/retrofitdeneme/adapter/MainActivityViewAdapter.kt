package com.example.retrofitdeneme.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitdeneme.MainActivity
import com.example.retrofitdeneme.adapter.MainActivityViewAdapter.*
import com.example.retrofitdeneme.databinding.AdapterMovieBinding
import com.example.retrofitdeneme.interfaces.OnMovieListener
import com.example.retrofitdeneme.model.MovieModelFav

class MainActivityViewAdapter : RecyclerView.Adapter<ItemHolder>() {

     private var movies = mutableListOf<MovieModelFav>()
    private val onMovieStar: OnMovieListener = MainActivity()
    fun setMovieList(movies: List<MovieModelFav>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }
    class ItemHolder(val binding: AdapterMovieBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterMovieBinding.inflate(inflater,parent,false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val movie = movies[position]
        holder.binding.name.text = movie.movie!!.title
        val posterURL = "https://image.tmdb.org/t/p/w500"+ movie.movie!!.posterPath
        Glide.with(holder.itemView.context).load(posterURL).into(holder.binding.imageview)
        if (movie.isStar){
            ImageViewCompat.setImageTintList(holder.binding.favView, ColorStateList.valueOf(Color.parseColor("#ffffff")))
        }else{

            ImageViewCompat.setImageTintList(holder.binding.favView, ColorStateList.valueOf(Color.parseColor("#000000")))
        }
        holder.binding.deleteView.setOnClickListener {
            onMovieStar.deleteMovie(movie)
        }
        holder.binding.favView.setOnClickListener {
            onMovieStar.onMovieStar(movie)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}