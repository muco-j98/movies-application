package com.example.movies_application.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movies_application.network.models.MoviesItem

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MoviesItem)

    @Delete
    suspend fun deleteMovie(movie: MoviesItem)

    @Query("SELECT * FROM movies_table WHERE userId = :userId ORDER BY watchlistTimeStamp DESC")
    fun getAllWatchlistMovies(userId: String): LiveData<List<MoviesItem>>

    @Query("SELECT EXISTS(SELECT * FROM movies_table WHERE id LIKE :movieId AND userId = :userId)")
    suspend fun checkIfMovieExists(movieId: String, userId: String): Boolean
}