package com.example.retrofitdeneme.service

import com.example.retrofitdeneme.BuildConfig
import com.example.retrofitdeneme.model.MovieList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface  RetrofitService {

    @GET("movie/popular")
     fun getAllMovies(@Query("api_key")apiKey:String): Call<MovieList>

    companion object{
        private var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {
            if (retrofitService == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}