package com.example.marvelheroes.models

import com.example.marvelheroes.data.HeroDTO

sealed class MainState {

    data object Loading : MainState()

    data class Success(
        val heroes: List<HeroDTO>,
    ) : MainState()

    data object Error : MainState()
}