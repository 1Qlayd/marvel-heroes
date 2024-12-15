package com.example.marvelheroes.data

data class MarvelResponse(
    val data: Data
)

data class Data(
    val results: List<Hero>
)

data class Hero(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)

data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun fullUrl(): String = "$path.$extension"
}