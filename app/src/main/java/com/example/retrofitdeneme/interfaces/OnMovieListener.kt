package com.example.retrofitdeneme.interfaces

import com.example.retrofitdeneme.model.MovieModelFav

interface OnMovieListener {
    fun onMovieStar(movie: MovieModelFav){}
    fun deleteMovie(movie: MovieModelFav){}
}
