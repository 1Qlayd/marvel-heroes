package com.example.marvelheroes.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HeroViewModelFactory(
    private val heroId: String,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HeroViewModel(heroId) as T
    }
}