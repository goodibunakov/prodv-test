package ru.goodibunakov.prodvtest.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.goodibunakov.prodvtest.model.WeatherForecastModel;
import ru.goodibunakov.prodvtest.model.WeatherModel;

public interface ApiService {

    @GET("weather")
    Call<WeatherModel> getToday(@Query("q") String city);

    @GET("forecast")
    Call<WeatherForecastModel> getForecast(@Query("q") String city);
}
