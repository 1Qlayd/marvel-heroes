package com.example.marvelheroes.models

import com.example.marvelheroes.data.HeroDTO

sealed class HeroState {
    data object Loading : HeroState()
    data class Success(
        val superhero: HeroDTO,
    ) : HeroState()

    data object Error : HeroState()
}