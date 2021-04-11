package com.example.movies_application.network.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies_table")
data class MoviesItem(
    var actors: List<String>,
    var averageRating: Int,
    var contentRating: String,
    var duration: String,
    var genres: List<String>,
    @PrimaryKey
    var id: String,
    var imdbRating: String,
    var originalTitle: String,
    var poster: String,
    var posterurl: String,
    var ratings: List<Int>,
    var releaseDate: String,
    var storyline: String,
    var title: String,
    var year: String,
    var watchlistTimeStamp: Long,
    @Ignore var avgRating: Double = 0.0
): Parcelable {

    constructor(): this(listOf(), 0, "", "", listOf(), "",
    "", "", "", "", listOf(), "", "",
    "", "", 0L)

    fun calcAverageRatingValue(): String {
        avgRating = ratings.average()
        return if (avgRating > avgRating.toInt()) {
//            TODO("fix for x.0xxx cases")
            String.format("%.1f", avgRating)
        } else {
            String.format("%.0f", avgRating)
        }
    }
}