package com.example.retrofitdeneme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdeneme.adapter.MainActivityViewAdapter
import com.example.retrofitdeneme.databinding.ActivityMainBinding
import com.example.retrofitdeneme.interfaces.OnMovieListener
import com.example.retrofitdeneme.model.MovieModelFav
import com.example.retrofitdeneme.repository.MainRepository
import com.example.retrofitdeneme.service.RetrofitService
import com.example.retrofitdeneme.viewmodel.MainActivityViewModel
import com.example.retrofitdeneme.viewmodel.MainActivityViewModelFactory

class MainActivity : AppCompatActivity(), OnMovieListener {
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var mainActivityBinding: ActivityMainBinding
    companion object{
        private  var mainActivityViewModel: MainActivityViewModel ?= null
        private  var movieList: List<MovieModelFav> ?= null
    }
    private lateinit var adapter: MainActivityViewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        mainActivityViewModel = ViewModelProvider(this,MainActivityViewModelFactory(MainRepository(retrofitService))).get(MainActivityViewModel::class.java)
        observers()
        adapter = MainActivityViewAdapter()
        mainActivityBinding.recyclerview.adapter = adapter
        mainActivityViewModel!!.getAllMovies()
    }

    private fun observers() {
            forLifeCycle()
    }

    private fun forLifeCycle() {
        mainActivityViewModel!!.movieListResponse.observe(this) { event ->
            when (event) {
                is MainActivityViewModel.MovieListEvent.Success -> {

                    Toast.makeText(this, "successful", Toast.LENGTH_SHORT)
                        .show()
                    movieList = event.MovieListResponse!!
                    adapter.setMovieList(movieList!!)
                }
                is MainActivityViewModel.MovieListEvent.Failure -> {
                    Toast.makeText(this, event.errorResponse, Toast.LENGTH_SHORT)
                        .show()
                }
                is MainActivityViewModel.MovieListEvent.Loading -> {
                    if (event.isLoad){
                        mainActivityBinding.load.visibility = View.VISIBLE
                    }else{
                        mainActivityBinding.load.visibility = View.INVISIBLE
                    }
                }

                is MainActivityViewModel.MovieListEvent.Empty -> {
                    // no-op
                }
                else -> {
                    // no-op
                }
            }
        }
    }

    override fun onMovieStar(movie: MovieModelFav) {
        mainActivityViewModel!!.setMovieStar(movie, movieList!!)
    }

    override fun deleteMovie(movie: MovieModelFav) {
        mainActivityViewModel!!.delete(movie, movieList!!)
    }
}