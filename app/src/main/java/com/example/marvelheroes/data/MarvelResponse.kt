package com.example.marvelheroes.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelResponse(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val etag: String,
    val data: Data?
)

@JsonClass(generateAdapter = true)
data class Data(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Hero>?
)

@JsonClass(generateAdapter = true)
data class Hero(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: Thumbnail?,
    val resourceURI: String,
    val comics: Comics?,
    val series: Series?,
    val stories: Stories?
)

@JsonClass(generateAdapter = true)
data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun fullUrl(): String = "$path.$extension"
}

@JsonClass(generateAdapter = true)
data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<ComicItem>?
)

@JsonClass(generateAdapter = true)
data class ComicItem(
    val resourceURI: String,
    val name: String
)

@JsonClass(generateAdapter = true)
data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<SeriesItem>?
)

@JsonClass(generateAdapter = true)
data class SeriesItem(
    val resourceURI: String,
    val name: String
)

@JsonClass(generateAdapter = true)
data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<StoryItem>?
)

@JsonClass(generateAdapter = true)
data class StoryItem(
    val resourceURI: String,
    val name: String,
    val type: String
)
