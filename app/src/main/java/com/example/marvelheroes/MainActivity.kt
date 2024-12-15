package com.example.marvelheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marvelheroes.screens.HeroesScreen
import com.example.marvelheroes.screens.HeroDetailsScreen
import com.example.marvelheroes.ui.theme.MarvelHeroesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelHeroesTheme {
                MarvelApp("0665987211e5f9db5aa80dc61dfd66bc")
            }
        }
    }
}

@Composable
fun MarvelApp(apikey: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "heroes") {
        composable("heroes") {
            HeroesScreen(navController = navController, apikey = "0665987211e5f9db5aa80dc61dfd66bc")
        }
        composable("heroDetails/{heroId}") { backStackEntry ->
            val heroId = backStackEntry.arguments?.getString("heroId")?.toInt() ?: 0
            HeroDetailsScreen(heroId = heroId, navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MarvelHeroesTheme {
        MarvelApp("0665987211e5f9db5aa80dc61dfd66bc")
    }
}