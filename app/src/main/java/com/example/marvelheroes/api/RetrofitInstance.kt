import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.nio.charset.StandardCharsets
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.marvelheroes.api.marvelApi


object RetrofitInstance {
    private const val publicKey = "0665987211e5f9db5aa80dc61dfd66bc"
    private const val privateKey = "576892428e3460628065a626ec444a865bdc68c4"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .addInterceptor(MarvelApiInterceptor(publicKey, privateKey))
        .build()


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api: marvelApi by lazy { retrofit.create(marvelApi::class.java) }
}

class MarvelApiInterceptor(private val publicKey: String, private val privateKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()

        val timestamp = System.currentTimeMillis().toString()
        val hash = generateMd5Hash(timestamp,privateKey,publicKey)

        url.addQueryParameter("apikey", publicKey)
        url.addQueryParameter("ts", timestamp)
        url.addQueryParameter("hash", hash)

        val request = originalRequest.newBuilder().url(url.build()).build()
        return chain.proceed(request)
    }

    private fun generateMd5Hash(timestamp: String,privateKey:String, publicKey: String): String {
        val stringToHash = timestamp + privateKey + publicKey
        val md5 = MessageDigest.getInstance("MD5")
        val bytes = md5.digest(stringToHash.toByteArray(StandardCharsets.UTF_8))
        return bytes.joinToString("") { String.format("%02x", it) }
    }
}