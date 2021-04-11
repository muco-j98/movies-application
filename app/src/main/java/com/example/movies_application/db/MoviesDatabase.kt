package com.example.movies_application.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movies_application.network.models.MoviesItem
import com.example.movies_application.util.Converters

@Database(
    entities = [MoviesItem::class],
    version = 10,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun getMoviesDao(): MovieDAO
}