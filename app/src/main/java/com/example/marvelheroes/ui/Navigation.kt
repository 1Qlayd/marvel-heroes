package com.example.marvelheroes.ui

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvelheroes.ui.HeroDetailScreen.heroID
import com.example.marvelheroes.ui.screens.HeroDetailScreen
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import com.example.marvelheroes.ui.screens.HeroListScreen

object HeroListScreen {

    fun route(): String {
        return "main"
    }
}

object HeroDetailScreen {

    const val heroID = "id"

    fun route(): String {
        return "hero?$heroID={$heroID}"
    }

    fun withHeroId(id: String): String {
        return "hero?$heroID=$id"
    }
}

@Composable
fun MarvelApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HeroListScreen.route(),
        enterTransition = { scaleIn() },
    ) {
        composable(HeroListScreen.route()) {
            HeroListScreen(navController = navController)
        }
        composable(
            route = HeroDetailScreen.route(),
            arguments = listOf(
                navArgument(heroID) { type = NavType.StringType },
            )
        ) { backStackEntry ->
            HeroDetailScreen(
                navController = navController,
                heroId = backStackEntry.arguments?.getString(heroID)
                    ?: error("No value passed for $heroID"),
            )
        }
    }
}
