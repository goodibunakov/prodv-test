package ru.goodibunakov.prodvtest.presentation.view.fragments.main

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.goodibunakov.prodvtest.web.dto.WeatherModel

@StateStrategyType(value = AddToEndStrategy::class)
interface MainFragmentView: MvpView {
    fun setCityName(cityName: String)
    fun showProgressBar()
    fun hideProgressBar()
    fun showErrorToast()
    fun setData(data: WeatherModel)
    fun fillSheet(forecast: Array<String>)
}