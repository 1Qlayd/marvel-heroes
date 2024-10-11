package com.example.marvelheroes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.marvelheroes.data.Hero

@Composable
fun HeroIcon(hero: Hero, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(600.dp)
            .clickable { onClick() }
    ) {
        AsyncImage (
            model = hero.imageUrl,
            contentDescription = "Hero Image",
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .align(Alignment.Center),
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