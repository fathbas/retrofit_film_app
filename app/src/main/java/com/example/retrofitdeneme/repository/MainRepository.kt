package com.example.retrofitdeneme.repository

import com.example.retrofitdeneme.BuildConfig
import com.example.retrofitdeneme.service.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
     fun getAllMovies() = retrofitService.getAllMovies(BuildConfig.API_KEY)
}