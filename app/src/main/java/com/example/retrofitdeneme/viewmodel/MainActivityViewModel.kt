package com.example.retrofitdeneme.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import com.example.retrofitdeneme.repository.MainRepository
import com.example.retrofitdeneme.util.SingleEvent
import com.example.retrofitdeneme.model.MovieList
import com.example.retrofitdeneme.model.MovieModelFav
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(private val mainRepository: MainRepository): ViewModel() {
    val movieListResponse: SingleEvent<MovieListEvent> by lazy { SingleEvent() }


     fun getAllMovies(){
         movieListResponse.value = MovieListEvent.Loading(true)
         val movieList: MutableList<MovieModelFav> = mutableListOf()
        val response = mainRepository.getAllMovies()
         response.enqueue(object : Callback<MovieList> {
             override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                 response.body()!!.movies!!.forEach {
                     val movieModel = MovieModelFav(false,it)
                     movieList.add(movieModel)
                 }
                 movieListResponse.value = MovieListEvent.Success(movieList)
                 movieListResponse.value = MovieListEvent.Loading(false)
             }
             override fun onFailure(call: Call<MovieList>, t: Throwable) {
                 movieListResponse.value = MovieListEvent.Failure(t.message.toString())
                 movieListResponse.value = MovieListEvent.Loading(false)
             }
         })
    }

    fun setMovieStar(model: MovieModelFav, movieList: List<MovieModelFav>) {
        movieListResponse.value = MovieListEvent.Loading(true)
        val index = movieList.indexOf(model)
        movieList[index].isStar = !movieList[index].isStar
        val list = movieList.toMutableList()
        val sortedList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sortedWith(compareBy<MovieModelFav> {it.isStar}.reversed())
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        movieListResponse.value = MovieListEvent.Success(sortedList)
        movieListResponse.value = MovieListEvent.Loading(false)
    }
    fun delete(model: MovieModelFav, movieList: List<MovieModelFav>) {
        movieListResponse.value = MovieListEvent.Loading(true)
        val list = movieList.toMutableList()
        list.remove(model)
        movieListResponse.value = MovieListEvent.Success(list)
        movieListResponse.value = MovieListEvent.Loading(false)
    }


    sealed class MovieListEvent {
        class Success(val MovieListResponse: List<MovieModelFav>?) : MovieListEvent()
        class Failure(val errorResponse: String) : MovieListEvent()
        class Loading(val isLoad: Boolean) : MovieListEvent()
        object Empty : MovieListEvent()
    }
}