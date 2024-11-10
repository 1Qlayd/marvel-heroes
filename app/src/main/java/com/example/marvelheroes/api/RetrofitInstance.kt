import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.marvelheroes.api.marvelApi

object RetrofitInstance {
    private const val BASE_URL = "https://gateway.marvel.com"

    val api: marvelApi by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(marvelApi::class.java)
    }
}