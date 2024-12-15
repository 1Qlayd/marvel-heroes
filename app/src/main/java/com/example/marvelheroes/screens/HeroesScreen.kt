package com.example.marvelheroes.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.marvelheroes.R
import com.example.marvelheroes.components.HeroIcon
import com.example.marvelheroes.data.Hero


@Composable
fun HeroesScreen(navController: NavController, apikey: String, limit: Int = 20) {
    var heroes by remember { mutableStateOf<List<Hero>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        try {
            Log.d("MyCoroutineTag", "Message from coroutine")
            val response = RetrofitInstance.api.getHeroes(limit = 20)
            Log.d("MyCoroutineTag", "1 - Код ответа: ${response.code()}")
            Log.d("MyCoroutineTag", "1 - Сообщение ответа: ${response.message()}")
            Log.d("MyCoroutineTag", "1 - Тело ответа: ${response.body()?.toString()}")
            if (response.isSuccessful) {
                heroes = response.body()?.data?.results?.map { result ->
                    Hero(
                        id = result.id,
                        name = result.name,
                        description = result.description,
                        thumbnail = result.thumbnail,
                    )
                } ?: emptyList()
            } else {
                error = "Ошибка загрузки данных: ${response.code()} - ${response.message()}"
            }
        } catch (e: Exception) {
            error = "Ошибка сети: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.marvel_logo),
            contentDescription = "Marvel Logo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(
            text = "Choose your hero",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (error != null) {
            Text(text = error!!, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(heroes) { hero ->
                    HeroIcon(hero) {
                        navController.navigate("heroDetails/${hero.id}")
                    }
                }
            }
        }
    }
}

