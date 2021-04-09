package com.example.movies_application.network.models

data class MoviesItem(
    val actors: List<String>,
    val averageRating: Int,
    val contentRating: String,
    val duration: String,
    val genres: List<String>,
    val id: String,
    val imdbRating: String,
    val originalTitle: String,
    val poster: String,
    val posterurl: String,
    val ratings: List<Int>,
    val releaseDate: String,
    val storyline: String,
    val title: String,
    val year: String
) {
    fun getAverageRating(): String {
        val avgRating = ratings.average()
        return if (avgRating > avgRating.toInt()) {
            TODO("fix for x.0xxx cases")
            String.format("%.1f", avgRating)
        } else {
            String.format("%.0f", avgRating)
        }
    }
}