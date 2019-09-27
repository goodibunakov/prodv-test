package ru.goodibunakov.prodvtest.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.goodibunakov.prodvtest.Constants
import ru.goodibunakov.prodvtest.view.MainActivity
import ru.goodibunakov.prodvtest.R
import ru.goodibunakov.prodvtest.model.List
import ru.goodibunakov.prodvtest.model.WeatherForecastModel
import ru.goodibunakov.prodvtest.model.WeatherModel
import ru.goodibunakov.prodvtest.utils.DateUtils
import ru.goodibunakov.prodvtest.utils.ImageUtils
import ru.goodibunakov.prodvtest.utils.TranslateUtils
import java.util.*


class MainFragment : Fragment() {

    private lateinit var city: String
    private var imageId: Int = 0
    private var forecastTexts: Array<String> = Array(5) { "" }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            updateScreen()
        } else {
            setRestoredData(savedInstanceState)
        }
    }

    private fun setRestoredData(savedInstanceState: Bundle) {
        cityText!!.text = savedInstanceState.getString(Constants.KEY_CITY)
        tempText!!.text = savedInstanceState.getString(Constants.KEY_TEMP)
        if (savedInstanceState.getInt(Constants.KEY_IMAGE_ID) != 0) {
            imageView!!.setImageResource(savedInstanceState.getInt(Constants.KEY_IMAGE_ID))
        }

        forecastTexts = savedInstanceState.getStringArray(Constants.KEY_FORECAST_ARRAY)!!
        if (forecastTexts.size == 5) {
            sheetText1!!.text = forecastTexts[0]
            sheetText2!!.text = forecastTexts[1]
            sheetText3!!.text = forecastTexts[2]
            sheetText4!!.text = forecastTexts[3]
            sheetText5!!.text = forecastTexts[4]
        }
    }

    fun updateScreen() {
        city = TranslateUtils.fromRuToEng()
        getTodayWeather()
        getForecastWeather()
    }

    private fun getTodayWeather() {
        progressBar!!.visibility = View.VISIBLE
        val callToday = (activity as MainActivity).apiService.getToday(city)
        callToday.enqueue(object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                val data: WeatherModel? = response.body()
                progressBar!!.visibility = View.INVISIBLE

                if (response.isSuccessful) {
                    if (data != null) {
                        Log.d("debug", "WeatherModel = $data")
                        cityText!!.text = TranslateUtils.fromEngToRu(data.name!!)
                        tempText!!.text = getString(R.string.gradus, String.format(Locale.getDefault(), "%.1f", data.main!!.temp))
                        imageId = ImageUtils.getImageDrawable(data.weather!![0].description!!)
                        imageView!!.setImageResource(imageId)
                    }
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                progressBar!!.visibility = View.INVISIBLE
                Toast.makeText(activity, resources.getString(R.string.download_error), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getForecastWeather() {
        progressBar!!.visibility = View.VISIBLE
        val callForecast = (activity as MainActivity).apiService.getForecast(city)
        callForecast.enqueue(object : Callback<WeatherForecastModel> {
            override fun onResponse(call: Call<WeatherForecastModel>, response: Response<WeatherForecastModel>) {
                val data = response.body()
                progressBar!!.visibility = View.INVISIBLE
                Log.d("debug", "response.body() = " + response.body()!!)
                if (response.isSuccessful) {
                    if (data != null) {
                        fillSheet(data)
                    }
                }
            }

            override fun onFailure(call: Call<WeatherForecastModel>, t: Throwable) {
                progressBar!!.visibility = View.INVISIBLE
                Toast.makeText(activity, resources.getString(R.string.download_error), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fillSheet(data: WeatherForecastModel) {
        // https://api.openweathermap.org/data/2.5/forecast?q=Prague&appid=b849169e8518a852747d3feae7403e88&units=metric&lang=ru
        val listFromData = data.list
        val dates = ArrayList<String>()
        for (list in listFromData!!) {
            val date = DateUtils.convertDate(list.dtTxt!!)
            if (!dates.contains(date)) {
                dates.add(date)
            }
        }

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
                    forecastTexts[i] = getString(R.string.sheet_line, dateSheet,
                            String.format(Locale.getDefault(), "%.1f", avgTemp),
                            String.format(Locale.getDefault(), "%.1f", avgWind),
                            String.format(Locale.getDefault(), "%.1f", avgPressure)
                    )
                }
            }
        }

        sheetText1!!.text = forecastTexts[0]
        sheetText2!!.text = forecastTexts[1]
        sheetText3!!.text = forecastTexts[2]
        sheetText4!!.text = forecastTexts[3]
        sheetText5!!.text = forecastTexts[4]
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.KEY_CITY, cityText!!.text.toString())
        outState.putString(Constants.KEY_TEMP, tempText!!.text.toString())
        outState.putInt(Constants.KEY_IMAGE_ID, imageId)
        outState.putStringArray(Constants.KEY_FORECAST_ARRAY, forecastTexts)
    }
}