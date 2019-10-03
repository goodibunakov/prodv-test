package ru.goodibunakov.prodvtest.domain

import io.reactivex.Observable
import ru.goodibunakov.prodvtest.presentation.model.WeatherForecastModel
import ru.goodibunakov.prodvtest.presentation.model.WeatherModel

interface Repository {
    fun getToday(city: String): Observable<WeatherModel>
    fun getForecast(city: String): Observable<WeatherForecastModel>
}