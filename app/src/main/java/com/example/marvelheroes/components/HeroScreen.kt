package com.example.marvelheroes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.example.marvelheroes.ui.theme.Choose
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.marvelheroes.R
import com.example.marvelheroes.data.heroes

@Composable
fun HeroesScreen(navController: NavController) {
    val lazyListState = rememberLazyListState();
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
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
                text = Choose,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
            LazyRow(
                state = lazyListState,
                flingBehavior = snapBehavior,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                items(heroes) { hero ->
                    HeroIcon(hero = hero) {
                        navController.navigate("heroDetails/${hero.name}")
                    }
                }
            }
        }
    }
}
