package com.example.marvelheroes.ui.screens

import com.example.marvelheroes.models.HeroViewModelFactory
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import com.example.marvelheroes.R
import com.example.marvelheroes.data.HeroDTO
import com.example.marvelheroes.models.HeroState
import com.example.marvelheroes.models.HeroViewModel


@Composable
fun HeroDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    heroId: String,
) {
    val viewModel: HeroViewModel = viewModel(factory = HeroViewModelFactory(heroId))
    val state by viewModel.stateFlow.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is HeroState.Loading -> LoadingIndicator()
            is HeroState.Error -> ErrorIndicator()
            is HeroState.Success -> {
                HeroDetailScreenContent(
                    navController = navController,
                    modifier = modifier,
                    hero = (state as HeroState.Success).superhero
                )
            }
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text(text = stringResource(R.string.error))
    }
}

@Composable
private fun HeroDetailScreenContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    hero: HeroDTO,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(model = hero.imageUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            BackButton(navController)
            Spacer(modifier = Modifier.weight(1f)) // Pushes content to the bottom

            HeroInfo(hero)

        }
    }
}

@Composable
private fun BackButton(navController: NavController) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
    }
}

@Composable
private fun HeroInfo(hero: HeroDTO) {
    Text(
        text = hero.name,
        fontFamily = FontFamily(Font(R.font.inter_28pt_extrabold)),
        fontSize = 38.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    Text(
        text = hero.description,
        fontFamily = FontFamily(Font(R.font.inter_28pt_bold)),
        fontSize = 26.sp,
        lineHeight = 36.sp,
        color = Color.White,
        textAlign = TextAlign.Center, // Center the text
    )
}

@Preview
@Composable
fun HeroDetailScreenPreview() {
    HeroDetailScreenContent(
        navController = rememberNavController(),
        hero = HeroDTO(
            id = "1",
            name = "Charles Xavier",
            description = "Head of X-Men",
            imageUrl = "https://i.pinimg.com/736x/1d/4f/15/1d4f15fd8b24a421ac403a511fe67451.jpg"
        )
    )
}
