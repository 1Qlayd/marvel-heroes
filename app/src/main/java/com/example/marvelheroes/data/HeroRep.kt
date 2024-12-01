package com.example.marvelheroes.data

interface HeroRep {
    suspend fun getHeroes(): List<HeroDTO>

    suspend fun getHeroById(id: String): HeroDTO
}