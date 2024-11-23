package com.example.marvelheroes.api

import com.example.marvelheroes.data.Hero
import com.example.marvelheroes.data.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface marvelApi {
    @GET("/v1/public/characters")
    suspend fun getHeroes(): Response<MarvelResponse>

    @GET("/v1/public/characters/{characterId}")
    suspend fun getHeroById(
        @Path("characterId") characterId: Int
    ): Response<Hero>
}
