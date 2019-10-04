package ru.goodibunakov.prodvtest.presentation.presenter

import io.reactivex.Scheduler
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.goodibunakov.prodvtest.domain.Mapper
import ru.goodibunakov.prodvtest.domain.Repository
import ru.goodibunakov.prodvtest.presentation.view.fragments.main.MainFragmentView
import ru.goodibunakov.prodvtest.utils.TranslateUtils
import ru.goodibunakov.prodvtest.web.dto.WeatherForecastModel

@InjectViewState
class MainFragmentPresenter() : MvpPresenter<MainFragmentView>() {

    private lateinit var main: Scheduler
    private lateinit var async: Scheduler
    private lateinit var repository: Repository
    private lateinit var mapper: Mapper<WeatherForecastModel, Array<String>>

    constructor(
            main: Scheduler,
            async: Scheduler,
            repository: Repository,
            mapper: Mapper<WeatherForecastModel, Array<String>>
    ) : this() {
        this.main = main
        this.async = async
        this.repository = repository
        this.mapper = mapper
    }

    fun updateScreen() {
        val cityName = TranslateUtils.fromRuToEng()
        viewState.setCityName(cityName)
        getTodayWeather(cityName)
        getForecastWeather(cityName)
    }

    private fun getTodayWeather(cityName: String) {
        viewState.showProgressBar()
        repository.getToday(cityName)
                .observeOn(main)
                .subscribeOn(async)
                .subscribe({ result ->
                    viewState.hideProgressBar()
                    viewState.setData(result)
                }, {
                    run {
                        viewState.hideProgressBar()
                        viewState.showErrorToast()
                    }
                })
    }

    private fun getForecastWeather(cityName: String) {
        viewState.showProgressBar()
        repository.getForecast(cityName)
                .observeOn(main)
                .subscribeOn(async)
                .subscribe({ result ->
                    viewState.hideProgressBar()
                    viewState.fillSheet(mapper.convert(result))
                }, {
                    viewState.hideProgressBar()
                    viewState.showErrorToast()
                })
    }
}