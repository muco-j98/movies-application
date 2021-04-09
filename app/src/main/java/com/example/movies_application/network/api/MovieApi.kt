package com.example.movies_application.network.api

import com.example.movies_application.network.models.MoviesItem
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {
    @GET("movies-coming-soon.json")
    suspend fun getMovies(): Response<List<MoviesItem>>
}