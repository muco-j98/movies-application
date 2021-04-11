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

    @Query("SELECT * FROM movies_table WHERE IS_WATCH_LISTED LIKE \"true\" ORDER BY watchlistTimeStamp DESC")
    fun getAllWatchlistMovies(): LiveData<List<MoviesItem>>

    @Query("SELECT EXISTS(SELECT * FROM movies_table WHERE id LIKE :movieId)")
    fun checkIfMovieExists(movieId: String): LiveData<Boolean>
}