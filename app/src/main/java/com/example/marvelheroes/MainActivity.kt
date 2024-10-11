package com.example.marvelheroes

import androidx.compose.material3.*
import android.os.Bundle
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter


data class Hero(val name: String, val imageUrl: String, val description: String)

val heroes = listOf(
    Hero("Deadpool", "https://iili.io/JMnAfIV.png", "No dead"),
    Hero("Iron Man", "https://iili.io/JMnuDI2.png", "Genius billionaire playboy philanthropist."),
    Hero("Spider-Man", "https://iili.io/JMnuyB9.png", "Teenager with spider-like abilities."),
)

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
        composable("heroesList") { HeroesListScreen(navController) }
        composable("heroDetails/{heroName}") { backStackEntry ->
            val heroName = backStackEntry.arguments?.getString("heroName") ?: return@composable
            val hero = heroes.find { it.name == heroName }
            if (hero != null) {
                HeroDetailsScreen(hero) {
                    navController.popBackStack()
                }
            }
        }
    }
}

@Composable
fun HeroesListScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.marvel_logo),
                contentDescription = "Marvel Logo",
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Choose your hero",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(heroes) { hero ->
                    HeroItem(hero) {
                        navController.navigate("heroDetails/${hero.name}")
                    }
                }
            }
        }
    }
}
@Composable
fun HeroItem(hero: Hero, onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .height(600.dp)
                .clickable { onClick() }
        ) {
            AsyncImage (
                model = hero.imageUrl,
                contentDescription = "Hero Image",
                modifier = Modifier.clip(RoundedCornerShape(24.dp)).align(Alignment.Center),
                contentScale = ContentScale.Crop
            )

            Text(
                text = hero.name,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = Color.White
            )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroDetailsScreen(hero: Hero, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(hero.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(hero.imageUrl),
                contentDescription = "Hero Logo",
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = hero.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}