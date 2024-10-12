package com.example.marvelheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marvelheroes.screens.HeroesScreen
import com.example.marvelheroes.data.heroes
import com.example.marvelheroes.screens.HeroDetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelApp()
        }
    }
}

@Composable
fun MarvelApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "heroesList") {
        composable("heroesList") {
            HeroesScreen(navController = navController)
        }
        composable("heroDetails/{heroName}") { backStackEntry ->
            val heroName = backStackEntry.arguments?.getString("heroName")
            val hero = heroes.find { it.name == heroName }
            if (hero != null) {
                HeroDetailsScreen(hero = hero, navController = navController)
            }
        }
    }
}