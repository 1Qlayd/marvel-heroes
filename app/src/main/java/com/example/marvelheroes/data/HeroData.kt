package com.example.marvelheroes.data

data class Hero(val name: String, val imageUrl: String, val description: String)

val heroes = listOf(
    Hero("Deadpool",
        "https://iili.io/JMnAfIV.png",
        "A hero who can't die and is very funny"),
    Hero("Iron Man",
        "https://iili.io/JMnuDI2.png",
        "Genius, billionaire, playboy, philanthropist."),
    Hero("Spider-Man",
        "https://iili.io/JMnuyB9.png",
        "Friendly neighborhood Spider-Man"),
)