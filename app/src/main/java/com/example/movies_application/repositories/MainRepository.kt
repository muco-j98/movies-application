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

    suspend fun checkIfMovieExists(movieId: String, userId: String) =
        movieDao.checkIfMovieExists(movieId, userId)

    fun getAllWatchlistMovies(userId: String) = movieDao.getAllWatchlistMovies(userId)

}