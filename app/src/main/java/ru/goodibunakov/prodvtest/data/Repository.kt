package ru.goodibunakov.prodvtest.data

import io.reactivex.Observable
import ru.goodibunakov.prodvtest.domain.Repository
import ru.goodibunakov.prodvtest.web.api.ApiService
import ru.goodibunakov.prodvtest.web.dto.WeatherForecastModel
import ru.goodibunakov.prodvtest.web.dto.WeatherModel

class Repository(private val apiService: ApiService) : Repository {

    override fun getToday(city: String): Observable<WeatherModel> {
        return apiService.getToday(city)
    }

    override fun getForecast(city: String): Observable<WeatherForecastModel> {
        return apiService.getForecast(city)
    }
}