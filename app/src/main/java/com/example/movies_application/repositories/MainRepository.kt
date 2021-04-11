package com.example.movies_application.repositories

import com.example.movies_application.db.MovieDAO
import com.example.movies_application.network.api.ApiHelper
import com.example.movies_application.network.models.MoviesItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val movieDao: MovieDAO,
    private val apiHelper: ApiHelper
) {
    suspend fun getMovies() = apiHelper.getMovies()

    suspend fun insertMovie(movie: MoviesItem) = movieDao.insertMovie(movie)

    suspend fun deleteMovie(movie: MoviesItem) = movieDao.deleteMovie(movie)

    fun checkIfMovieExists(movieId: String) = movieDao.checkIfMovieExists(movieId)

    fun getAllWatchlistMovies() = movieDao.getAllWatchlistMovies()

}