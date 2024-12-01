package com.example.marvelheroes.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelheroes.data.HeroRep
import com.example.marvelheroes.data.Retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val heroesRepository: HeroRep = Retrofit()
) : ViewModel() {

    private val mutableStateFlow: MutableStateFlow<MainState> = MutableStateFlow(MainState.Loading)
    val stateFlow: StateFlow<MainState> = mutableStateFlow

    init {
        viewModelScope.launch {
            try {
                val superheroes = heroesRepository.getHeroes()
                mutableStateFlow.value = MainState.Success(superheroes)
            } catch (ex: Exception) {
                mutableStateFlow.value = MainState.Error
            }
        }
    }
}
