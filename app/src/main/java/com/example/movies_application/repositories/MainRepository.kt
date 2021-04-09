package com.example.movies_application.repositories

import com.example.movies_application.network.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getMovies() = apiHelper.getMovies()
}