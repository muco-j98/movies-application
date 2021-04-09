package com.example.movies_application.network.api

import com.example.movies_application.network.models.MoviesItem
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: MovieApi
): ApiHelper {
    override suspend fun getMovies(): Response<List<MoviesItem>> = apiService.getMovies()
}