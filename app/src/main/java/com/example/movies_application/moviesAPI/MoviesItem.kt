package com.example.movies_application.moviesAPI

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
)