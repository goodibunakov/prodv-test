package ru.goodibunakov.prodvtest.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.goodibunakov.prodvtest.Constants
import ru.goodibunakov.prodvtest.model.WeatherForecastModel
import ru.goodibunakov.prodvtest.model.WeatherModel

interface ApiService {

    @GET("weather")
    fun getToday(@Query("q") city: String): Call<WeatherModel>

    @GET("forecast")
    fun getForecast(@Query("q") city: String): Call<WeatherForecastModel>

    companion object Factory {
        fun create(): ApiService {
            val httpClient = OkHttpClient.Builder()
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(httpLoggingInterceptor)
            httpClient.addInterceptor { chain ->
                var request = chain.request()
                val url = request.url.newBuilder()
                        .addQueryParameter("appid", Constants.API_KEY)
                        .addQueryParameter("units", "metric")
                        .addQueryParameter("lang", "ru")
                        .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}