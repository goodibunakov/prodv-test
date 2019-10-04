package ru.goodibunakov.prodvtest.domain

import io.reactivex.Observable
import ru.goodibunakov.prodvtest.web.dto.WeatherForecastModel
import ru.goodibunakov.prodvtest.web.dto.WeatherModel

interface Repository {
    fun getToday(city: String): Observable<WeatherModel>
    fun getForecast(city: String): Observable<WeatherForecastModel>
}