package ru.goodibunakov.prodvtest.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.goodibunakov.prodvtest.presentation.model.WeatherModel

@StateStrategyType(value = AddToEndStrategy::class)
interface MainFragmentView: MvpView {
    fun setCityName(cityName: String)
    fun showProgressBar()
    fun hideProgressBar()
    fun showErrorToast()
    fun setData(data: WeatherModel)
    fun fillSheet(forecast: Array<String>)
}