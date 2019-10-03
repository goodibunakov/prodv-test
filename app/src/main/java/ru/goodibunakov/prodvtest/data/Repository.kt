package ru.goodibunakov.prodvtest.data

import io.reactivex.Observable
import ru.goodibunakov.prodvtest.api.ApiService
import ru.goodibunakov.prodvtest.domain.Repository
import ru.goodibunakov.prodvtest.presentation.model.WeatherForecastModel
import ru.goodibunakov.prodvtest.presentation.model.WeatherModel

class Repository(private val apiService: ApiService) : Repository {

    override fun getToday(city: String): Observable<WeatherModel> {
        return apiService.getToday(city)
    }

    override fun getForecast(city: String): Observable<WeatherForecastModel> {
        return apiService.getForecast(city)
    }

}