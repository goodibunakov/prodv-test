package ru.goodibunakov.prodvtest.api

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.goodibunakov.prodvtest.presentation.model.WeatherForecastModel
import ru.goodibunakov.prodvtest.presentation.model.WeatherModel
import ru.goodibunakov.prodvtest.utils.Constants

interface ApiService {

    @GET("weather")
    fun getToday(@Query("q") city: String): Observable<WeatherModel>

    @GET("forecast")
    fun getForecast(@Query("q") city: String): Observable<WeatherForecastModel>

    companion object {
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
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}