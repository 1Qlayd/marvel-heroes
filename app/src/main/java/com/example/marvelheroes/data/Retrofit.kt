package com.example.marvelheroes.data

import com.example.marvelheroes.api.CharactersResponse
import com.example.marvelheroes.api.MarvelApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.math.BigInteger
import java.security.MessageDigest

private const val baseUrl = "https://gateway.marvel.com/v1/public/"
private const val publicKey= "d8ba7622c7fa52efad42ef8ae6adf313"
private const val privateKey = "143958aaf2089ece9d9481b3bb0434e9c85130ea"

class Retrofit : HeroRep {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val api: MarvelApi = retrofit.create()

    override suspend fun getHeroes(): List<HeroDTO> {
        val timestamp = System.currentTimeMillis()
        return try {
            val charactersResponse = api.getSuperheroes(
                apiKey = publicKey,
                timeStamp = timestamp,
                hash = md5(timestamp.toString() + privateKey + publicKey)
            )
            mapHeroes(charactersResponse)
        } catch (e: Exception) {
            println("Error fetching heroes: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getHeroById(id: String): HeroDTO {
        val timestamp = System.currentTimeMillis()
        return try {
            val charactersResponse = api.getSuperhero(
                heroId = id,
                apiKey = publicKey,
                timeStamp = timestamp,
                hash = md5(timestamp.toString() + privateKey + publicKey)
            )
            mapHeroes(charactersResponse).firstOrNull() ?: throw Exception("Hero not found")
        } catch (e: Exception) {
            println("Error fetching hero with id $id: ${e.message}")
            throw e
        }
    }

    private fun mapHeroes(heroRes: CharactersResponse): List<HeroDTO> {
        return heroRes.data.results.map { hero ->
            val imagePath = hero.image.path.replace("http://", "https://")
            HeroDTO(
                id = hero.id,
                imageUrl = (imagePath + "." + hero.image.extension),
                name = hero.name,
                description = hero.description,
            )
        }
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(
            1,
            md.digest(input.toByteArray())
        ).toString(16).padStart(32, '0')
    }
}
