package com.example.movies_application.util

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun listToString(actors: List<String>?): String? =
        actors?.joinToString(",") { it.subSequence(0,it.length) }

    @TypeConverter
    fun stringToList(actors: String?): List<String>? =
        actors?.split(',')?.map { "\"" + it + "\"" }?.toList()

    @TypeConverter
    fun ratingsToString(ratings: List<Int>?): String? =
        ratings?.joinToString( "," ) { it.toString() }

    @TypeConverter
    fun stringToListOfInt(ratings: String?): List<Int>? =
        ratings?.split(",")?.map { it.toInt() }?.toList()
}