package ru.goodibunakov.prodvtest.data

import android.content.Context
import ru.goodibunakov.prodvtest.R
import ru.goodibunakov.prodvtest.domain.Mapper
import ru.goodibunakov.prodvtest.presentation.model.List
import ru.goodibunakov.prodvtest.presentation.model.WeatherForecastModel
import ru.goodibunakov.prodvtest.utils.DateUtils
import java.util.*

class MapperToString(private val context: Context) : Mapper<WeatherForecastModel, Array<String>> {

    override fun convert(from: WeatherForecastModel): Array<String> {
        // https://api.openweathermap.org/data/2.5/forecast?q=Prague&appid=b849169e8518a852747d3feae7403e88&units=metric&lang=ru
        val listFromData = from.list
        val dates = ArrayList<String>()
        for (list in listFromData!!) {
            val date = DateUtils.convertDate(list.dtTxt!!)
            if (!dates.contains(date)) {
                dates.add(date)
            }
        }

        val forecastTexts: Array<String> = Array(5) { "" }

        if (dates.size > 0) {
            for (i in dates.indices) {
                val forecast = ArrayList<List>()
                val date = dates[i]
                for (list in listFromData) {
                    if (list.dtTxt!!.contains(date)) {
                        forecast.add(list)
                    }
                }

                var avgTemp = 0.0
                var avgWind = 0.0
                var avgPressure = 0.0

                for (list in forecast) {
                    avgTemp += list.main?.temp!!
                    avgWind += list.wind?.speed!!
                    avgPressure += list.main!!.pressure!!
                }

                avgTemp /= forecast.size
                avgWind /= forecast.size
                avgPressure = avgPressure / forecast.size.toDouble() / 1.333
                val dateSheet = DateUtils.convertDateForUI(forecast[0].dtTxt!!)

                if (i <= 4) {
                    forecastTexts[i] = context.getString(R.string.sheet_line, dateSheet,
                            String.format(Locale.getDefault(), "%.1f", avgTemp),
                            String.format(Locale.getDefault(), "%.1f", avgWind),
                            String.format(Locale.getDefault(), "%.1f", avgPressure)
                    )
                }
            }
        }
        return forecastTexts
    }
}