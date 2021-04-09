package com.example.movies_application.network.api

import com.example.movies_application.network.models.MoviesItem
import retrofit2.Response

interface ApiHelper {
    suspend fun getMovies(): Response<List<MoviesItem>>
}